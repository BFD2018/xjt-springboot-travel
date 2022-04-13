<template>
  <div class="route-list-view">
    <div class="route-list-header">
      <!-- 搜索 -->
      <el-input
        v-model="queryKeyForTitle"
        clearable
        size="medium"
        placeholder="按title模糊搜索"
        class="filter-item"/>

      <div class="filter-item" style="width: 150px">
        <el-select v-model="routeFlag" size="small"
                   placeholder="无状态"
                   @change="initTableData">
          <el-option label="无状态" value=""></el-option>
          <el-option label="上架" value="1"></el-option>
          <el-option label="下架" value="0"></el-option>
        </el-select>
      </div>

      <div class="filter-item">
        <el-select
          v-model="routeCategory"
          @change="initTableData" placeholder="请选择Route类别">
          <el-option
            v-for="(item,index) in $store.state.RouteCategories"
            :key="index"
            :label="item"
            :value="index + 1">
          </el-option>
        </el-select>
      </div>

      <div class="filter-item" style="flex:1;">
        <span>选择价格区间：</span>
        <span><el-input v-model="lowel_price" style="width:180px" placeholder="请输入最低价格"></el-input></span>
        <span> - </span>
        <span><el-input v-model="high_price" style="width:180px" placeholder="请输入最高价格"></el-input></span>
      </div>

      <div class="btn">
        <el-button @click="initTableData" type="success" size="mini" icon="el-icon-search">搜索</el-button>
      </div>
    </div>

    <div class="route-list">
      <el-table
        border
        stripe
        v-loading="tableLoading"
        tooltip-effect="dark"
        :data="tableData"
        style="width: 100%">
        <el-table-column
          width="90"
          label="ID">
          <template slot-scope="scope">
            {{scope.row.id}}
          </template>
        </el-table-column>

        <el-table-column
          label="路线名">
          <template slot-scope="scope">
            {{scope.row.title}}
          </template>
        </el-table-column>

        <el-table-column
          width="280"
          label="路线轮播图">
          <template slot-scope="scope">
            <el-carousel height="200px">
              <el-carousel-item v-for="(item,index) in scope.row.routePhoto.split(',').slice(0,3)" :key="item">
                <img v-lazy="item" alt="" style="border: 1px solid  #ddd;" width="260px" height="200px">
              </el-carousel-item>
            </el-carousel>
          </template>
        </el-table-column>

        <el-table-column
          width="80"
          label="价格">
          <template slot-scope="scope">
            ￥{{scope.row.price}}
          </template>
        </el-table-column>

        <el-table-column
          width="140"
          label="状态">
          <template slot-scope="scope">
            <el-switch
              @change="tableColumnSwitch(scope.$index, scope.row)"
              v-model="scope.row.flag"
              active-text="上架"
              inactive-text="下架">
            </el-switch>
          </template>
        </el-table-column>

        <el-table-column
          width="80"
          label="收藏数量">
          <template slot-scope="scope">
            {{scope.row.counts}}
          </template>
        </el-table-column>

        <el-table-column
          width="70"
          label="目的地">
          <template slot-scope="scope">
            {{scope.row.location}}
          </template>
        </el-table-column>

        <el-table-column
          width="80"
          label="分类">
          <template slot-scope="scope">
            <el-tag type="warning">{{$store.state.RouteCategories[scope.row.categoryId -1]}}</el-tag>
          </template>
        </el-table-column>

        <el-table-column
          width="80"
          label="店铺">
          <template slot-scope="scope">
            <el-tag type="success" @click="$router.push('')">{{scope.row.sellerId}}</el-tag>
          </template>
        </el-table-column>

        <el-table-column
          width="100"
          label="上架时间">
          <template slot-scope="scope">
            {{scope.row.publishTime |filterFormatDate(that) }}
          </template>
        </el-table-column>

        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑
            </el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!--分页-->
    <div class="route-list-pagination">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[3,6,9,12]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
  export default {
    name: "RouteList",
    components: {},
    data() {
      return {
        /*查询条件*/
        queryKeyForTitle: "",
        routeFlag: "",			//上架true 下架false
        routeCategory: "",
        lowel_price: "",
        high_price: "",

        currentTravellerForm: {},

        tableLoading: true,
        tableData: [],

        /*分页*/
        currentPage: 1,
        pageSize: 6,
        total: 0
      }
    },
    methods: {
      initTableData() {
        let params = {
          "title": this.queryKeyForTitle,
          "flag": this.routeFlag,
          "category_id": this.routeCategory,
          "lowel_price": this.lowel_price,
          "high_price": this.lowel_price,
          "currentPage": this.currentPage,
          "pageSize": this.pageSize
        };
        let baseUrl = "/route/queryByConditions?" + this.$qs.stringify(params, {encode: false});
        console.log(baseUrl);
        this.$getRequest(baseUrl).then(res => {
          console.log(res);
          if (res.data.status === 200) {
            this.tableData.splice(0);
            this.tableData.push(...res.data.obj.records);
            this.total = Number(res.data.obj.total);
            this.tableLoading = false;
          } else {
            this.$message.error(res.data.msg);
          }
        })
      },
      /*表格操作*/
      handleEdit(index, row) {
        console.log(row);
        this.currentTravellerForm = row;
      },
      handleDelete(index, row) {
        this.$confirm('此操作将永久删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$getRequest("/route/delete?id=" + row.id).then(res => {
            if (res.data.status === 200) {
              this.tableData.splice(index, 1);
              this.$message.success("删除成功！")
            }
          })
        }).catch(() => {
          this.$message.info('已取消删除');
        });
      },

      tableColumnSwitch(index, row) {
        console.log(row);
        this.$getRequest("/route/toggle/flag?id=" + row.id).then(res => {
          console.log(res);
          if (res.data.status === 200) {
            this.$message.success("更新成功！");
          }
        })
      },

      /*分页*/
      handleSizeChange(val) {
        this.pageSize = val;
        this.initTableData();
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.initTableData();
      },
    },
    created() {
      this.initTableData();
    }
  }
</script>

<style lang="less" scoped>
  .route-list-view {
    margin: 10px auto;
    width: 95%;

    .route-list-header {
      height: 60px;
      display: flex;
      background-color: #eee;

      .filter-item {
        width: 200px;
        height: 60px;
        line-height: 60px;
        vertical-align: middle;
        margin-left: 20px;

        input {
          width: 250px;
          height: 50px;
          line-height: 50px;
        }
      }

      .btn {
        line-height: 60px;
        margin-left: 20px;
      }
    }

    .route-list {
      margin-top: 10px;
    }

    .route-list-pagination {
      border: 1px solid #ddd;
      border-radius: 5px;
      padding: 5px;
      margin-top: 30px;
      margin-bottom: 30px;
      text-align: left;
    }
  }
</style>
