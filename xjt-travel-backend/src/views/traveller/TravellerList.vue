<template>
	<div class="traveller-list-view">
		<div class="traveller-header">
			<!-- 搜索 -->
			<el-input
					v-model="queryKeyForName"
					clearable
					size="small"
					placeholder="输入username模糊搜索"
					class="filter-item"/>

			<div class="btn">
				<el-button @click="initTableData" type="success" size="medium" icon="el-icon-search">搜索</el-button>
			</div>

			<div class="btn">
				<el-button type="warning" size="medium" @click="$router.push('/traveller/add')" icon="el-icon-plus">新增</el-button>
			</div>
		</div>

		<div class="traveller-list">
			<el-table
					border
					stripe
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
						width="150"
						label="Name">
					<template slot-scope="scope">
						{{scope.row.name}}
					</template>
				</el-table-column>

				<el-table-column
						width="140"
						label="Cover">
					<template slot-scope="scope">
						<img v-lazy="scope.row.avatar" alt="" style="border: 1px solid  #ddd;" width="120px" height="120px">
					</template>
				</el-table-column>

				<el-table-column
						label="Description">
					<template slot-scope="scope">
						{{scope.row.description}}
					</template>
				</el-table-column>

				<el-table-column label="操作" width="250">
					<template slot-scope="scope">
						<el-button
								size="mini"
								type="success"
								@click="$router.push('/traveller/articles/' + scope.row.id)">关联游记</el-button>
						<el-button
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
		<div class="traveller-list-pagination">
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

		<!--表单渲染-->
		<el-dialog append-to-body :visible.sync="addTravellerDialogVisible"
							 title='编辑信息'
							 @close="currentTravellerForm = {}"
							 :close-on-click-modal="false"
							 width="570px">
			<el-form :model="currentTravellerForm"
							 ref="ruleForm"
							 label-width="80px"
							 label-position="left"
							 size="small">
				<el-form-item label="ID">
					<el-input disabled v-model="currentTravellerForm.id" />
				</el-form-item>
				<el-form-item label="名称">
					<el-input v-model="currentTravellerForm.name" />
				</el-form-item>

				<el-form-item label="描述">
					<el-input
							type="textarea"
							:rows="4"
							placeholder="请输入内容"
							v-model="currentTravellerForm.description">
					</el-input>
				</el-form-item>
				<el-form-item label="头像">
					<el-input v-model="currentTravellerForm.avatar" />
					<br>
					<img v-lazy="currentTravellerForm.avatar" alt="" style="border: 1px solid  #ddd;" width="120px" height="120px">
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button type="primary" @click="handleSubmitDialog">提交</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
	export default {
		name: "TravellerList",
		components:{

		},
		data(){
			return{
				/*查询条件*/
				queryKeyForName:"",

				addTravellerDialogVisible:false,
				currentTravellerForm:{},

				tableLoading:true,
				tableData:[],

				/*分页*/
				currentPage:1,
				pageSize:6,
				total:0
			}
		},
		methods:{
			initTableData(){
				let params = {
					"name":this.queryKeyForName,
					"currentPage":this.currentPage,
					"pageSize":this.pageSize
				}
				let baseUrl = "/traveller/queryByConditions?" + this.$qs.stringify(params,{encode:false});

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
			handleEdit(index, row) {
				//编辑信息
				console.log(row);
				this.currentTravellerForm = row;
				this.addTravellerDialogVisible = true;
			},
			handleDelete(index, row) {
        // console.log(row);
        //删除
				this.$alert('删除旅行家及其所有关联游记，该操作不可恢复，请谨慎操作！', '确认删除该用户吗？', {
					confirmButtonText: '确定',
					callback: action => {
						this.$getRequest("/traveller/delete?id="+row.id).then(res =>{
							if(res.data.status === 200){
								this.tableUserData.splice(index,1);
								this.$message.success("用户删除成功！")
							}else{
							  this.$message.error(res.data.msg);
              }
						})
					}
				});
				console.log(index, row);
			},
			handleSubmitDialog(){
				this.$postRequest("/traveller/update",this.currentTravellerForm).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.$message.success("更新成功！")
						this.addTravellerDialogVisible = false;
						this.currentTravellerForm = {};
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
	.traveller-list-view{
		margin: 10px auto;
		width: 95%;
		height: 100%;
		.traveller-header {
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

		.traveller-list-pagination{
			border: 1px solid #ddd;
			border-radius: 5px;
			padding: 5px;
			margin-top: 30px;
			margin-bottom: 30px;
			text-align: left;
		}
	}
</style>
