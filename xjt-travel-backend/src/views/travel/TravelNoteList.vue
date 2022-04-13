<template>
	<div class="travelnote-list-view">
		<div class="travelnote-header">
			<!-- 搜索 -->
			<el-input
					v-model="queryKeyForName"
					clearable
					size="small"
					placeholder="输入username模糊搜索"
					class="filter-item"/>

			<el-select v-model="travelType" size="small"
								 placeholder="无状态"
								 class="filter-item"
								 style="width: 90px"
								 @change="initTableData">
				<el-option label="无状态" value=""></el-option>
				<el-option label="热门游记" value="1"></el-option>
				<el-option label="最新发布" value="2"></el-option>
			</el-select>

			<div class="btn">
				<el-button @click="searchBtnClick" type="success" size="medium" icon="el-icon-search">搜索</el-button>
			</div>

			<div class="btn">
				<el-button type="warning" size="medium" @click="$router.push('/travel/write')" icon="el-icon-plus">新增</el-button>
			</div>
		</div>

		<div class="travelnote-list">
			<el-table
					border
					v-loading="tableLoading"
					tooltip-effect="dark"
					:data="tableData"
					style="width: 100%">
				<el-table-column
						width="100"
						label="ID">
					<template slot-scope="scope">
						{{scope.row.id}}
					</template>
				</el-table-column>

				<el-table-column
						:show-overflow-tooltip="true"
						width="300"
						label="title">
					<template slot-scope="scope">
						{{scope.row.title}}
					</template>
				</el-table-column>

				<el-table-column
						width="140"
						label="cover">
					<template slot-scope="scope">
						<img v-lazy="scope.row.coverImage" alt="" style="border: 1px solid  #ddd;" width="120px" height="120px">
					</template>
				</el-table-column>

				<el-table-column
						label="description">
					<template slot-scope="scope">
						{{scope.row.description}}
					</template>
				</el-table-column>

				<el-table-column
						:show-overflow-tooltip="true"
						width="100"
						label="location">
					<template slot-scope="scope">
						{{scope.row.location}}
					</template>
				</el-table-column>

				<el-table-column
						width="80"
						label="UP">
					<template slot-scope="scope">
						{{scope.row.up}}
					</template>
				</el-table-column>

				<el-table-column label="操作" width="220">
					<template slot-scope="scope">
						<el-button type="success" size="mini" @click="previewTravel(scope.row)">预览</el-button>
						<el-button
								type="warning"
								size="mini"
								@click="handleEdit(scope.$index, scope.row)">编辑</el-button>
						<el-button
								size="mini"
								type="danger"
								@click="handleDelete(scope.$index, scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>

		<!--分页-->
		<div class="my-pagination">
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

		<div style="height: 30px;"></div>

		<!--预览-dialog-->
		<el-dialog :title="'预览游记：' + noteTitle"
							 width="70%"
							 append-to-body
							 :visible.sync="previewTravelNoteDialogVisible">
			<div class="note-detail" v-html="noteDetail"></div>
		</el-dialog>
	</div>
</template>

<script>
	export default {
		name: "TravelNoteList",
		components:{

		},
		data(){
			return{
				/*查询条件*/
				queryKeyForName:"",
				travelType:"",

				tableLoading:true,
				tableData:[],
				//预览
				previewTravelNoteDialogVisible:false,
				noteTitle:"",
				noteDetail:"",

				/*分页*/
				currentPage:1,
				pageSize:6,
				total:0
			}
		},
		methods:{
			initTableData(){
				let params = {
					"type":this.travelType,
					"title":this.queryKeyForName,
					"currentPage":this.currentPage,
					"pageSize":this.pageSize
				}
				let baseUrl = "/travelNote/getByTitleType?" + this.$qs.stringify(params,{encode:false});

				this.$getRequest(baseUrl).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.tableData.splice(0);
						this.tableData.push(...res.data.obj.records);
						this.total = Number(res.data.obj.total);
						this.tableLoading = false;
					}else{
						this.$message.error(res.data.msg);
					}
				})
			},
			/*表格操作*/
			//编辑
			handleEdit(index, row) {
				console.log(row);
				this.$router.push({
					name:"新增游记",
					params:row,
				})
			},
			//删除
			handleDelete(index, row) {
				this.$alert('删除后不可恢复，请谨慎操作！', '确认删除吗？', {
					confirmButtonText: '确定',
					callback: action => {
						this.$getRequest("/travelNote/delete?id="+row.id).then(res =>{
							if(res.data.status === 200){
								this.tableUserData.splice(index,1);
								this.$message.success("用户删除成功！")
							}
						})
					}
				});
				// console.log(index, row);
			},
			//预览
			previewTravel(row){
				this.noteTitle = row.title;
				this.$getRequest("/travelNote/detail?tn_id="+row.id).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.noteDetail = res.data.obj.content;
						this.previewTravelNoteDialogVisible = true;
					}else{
						this.$message.error(res.data.msg);
					}
				})
			},
      searchBtnClick(){
			  this.currentPage = 1;
			  this.pageSize = 6;
			  this.initTableData();
      },

			/*分页*/
			handleSizeChange(val) {
				console.log(`每页 ${val} 条`);
				this.pageSize = val;
				this.initTableData();
			},
			handleCurrentChange(val) {
				console.log(`当前页: ${val}`);
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
	.travelnote-list-view{
		margin: 10px auto;
		width: 95%;
		.travelnote-header {
			height: 60px;
			display: flex;
			background-color: #eee;
			padding: 0 30px;
			.filter-item {
				width: 250px;
				line-height: 60px;
				vertical-align: middle;
				margin: 0 3px 10px 0;
				input {
					width: 250px;
					height: 40px;
					line-height: 40px;
				}
			}
			.btn{
				line-height: 60px;
				margin-left: 20px;
			}
		}
	}
</style>
