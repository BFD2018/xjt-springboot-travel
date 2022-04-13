<template>
  <div class="route-add-view">
    <h2>新增旅行路线</h2>
    <el-form ref="editForm"
             status-icon
             label-width="100px"
             label-position="left"
             :model="editForm">
      <el-form-item label="路线名称"
                    :rules="[
											{ required: true, message: '请输入名称', trigger: 'blur' },
										]">
        <el-input v-model="editForm.title"></el-input>
      </el-form-item>

      <el-form-item label="路线封面图">
        <el-input v-model="editForm.route_photo" aria-placeholder="图片以逗号分隔"></el-input>
      </el-form-item>

      <el-form-item label="价格">
        <el-input v-model="editForm.price"></el-input>
      </el-form-item>

      <el-form-item label="是否上架">
        <el-switch
          style="display: block"
          v-model="editForm.flag"
          active-color="#13ce66"
          inactive-color="#ff4949"
          active-text="在售"
          inactive-text="下架">
        </el-switch>
      </el-form-item>

      <el-form-item label="目的地">
        <el-input v-model="editForm.location"></el-input>
      </el-form-item>

      <el-form-item label="分类">
        <el-select
          v-model="editForm.routeCategory"
          placeholder="请选择Route类别">
          <el-option
            v-for="(item,index) in $store.state.RouteCategories"
            :key="index"
            :label="item"
            :value="index">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="旅行社">
        <el-select
          v-model="editForm.seller"
          placeholder="请选择旅行社">
          <el-option
            v-for="(item,index) in sellerList"
            :key="index"
            :label="item.name"
            :value="item.sellerId">
            <span style="float: left">{{ item.name }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">{{ item.sellerId }}</span>
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="路线简介">
        <el-input
          type="textarea"
          :rows="4"
          placeholder="请输入内容"
          v-model="editForm.description">
        </el-input>
      </el-form-item>

      <el-form-item style="margin: auto;">
        <el-button type="info" @click="reset">重置</el-button>
        <el-button type="primary" @click="submitForm">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
	export default {
		name: "RouteAdd",
    data() {
      return {
        editForm: {
          title: "",
          route_photo: "",
          price: "",
          flag: true,
          location: "",
          routeCategory: "",
          seller: "",
          description: "",
        },
        sellerList:[],    //旅行社

        /*wangEditor*/
        editor:null,
      }
    },
    methods: {
      submitForm() {
        this.$postRequest("/route/add", this.editForm).then(res => {
          console.log(res);
          if (res.data.status === 200) {
            this.$notify.success(res.data.msg);
            this.$router.push('/trips/list')
          } else {
            this.$notify.success(res.data.msg);
            return false;
          }
        });
      },
      //重置（清空输入）
      reset() {
        this.$refs.editForm.resetFields();
      },

      changeFlag(status){
        console.log("当前状态---",status);
      },

      getSellerData(){
        this.$getRequest("/seller/all").then(res =>{
          console.log(res);
          if(res.data.status === 200){
            this.sellerList.splice(0);
            this.sellerList.push(...res.data.obj);
          }else{
            this.$message.error(res.data.msg);
          }
        })
      },

    },
    created() {
      this.getSellerData();
    },
	}
</script>

<style scoped>
  .route-add-view {
    padding: 20px 0;
    margin: 100px auto;
    width: 50%;
  }
</style>
