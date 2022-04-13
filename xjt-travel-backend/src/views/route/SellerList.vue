<template>
	<div class="seller-list-view">
		<div class="route-list">
			<el-table
					border
					stripe
					v-loading="tableLoading"
					tooltip-effect="dark"
					:data="tableData"
					style="width: 100%">
				<el-table-column
						width="100"
						label="商家ID">
					<template slot-scope="scope">
						{{scope.row.sellerId}}
					</template>
				</el-table-column>

				<el-table-column
						width="200"
						label="名称">
					<template slot-scope="scope">
						{{scope.row.name}}
					</template>
				</el-table-column>

				<el-table-column
						width="200"
						label="全称">
					<template slot-scope="scope">
						{{scope.row.fullname}}
					</template>
				</el-table-column>

				<el-table-column
						width="160"
						label="Logo">
					<template slot-scope="scope">
						<img v-lazy="scope.row.logo" alt="" style="border: 1px solid  #ddd;" width="140px" height="140px">
					</template>
				</el-table-column>

				<el-table-column label="操作" width="280">
					<template slot-scope="scope">
						<el-button
								type="success"
								size="mini"
								@click="$router.push('/seller/routes?seller_id='+scope.row.sellerId)">关联路线</el-button>
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

		<!--表单渲染-->
		<el-dialog append-to-body :visible.sync="updateSellerDialogVisible"
							 title='编辑信息'
							 @close="currentSellerForm = {}"
							 :close-on-click-modal="false"
							 width="570px">
			<el-form :model="currentSellerForm"
							 ref="ruleForm"
							 label-width="80px"
							 label-position="left"
							 size="small">
				<el-form-item label="商家ID">
					<el-input disabled v-model="currentSellerForm.sellerId" />
				</el-form-item>
				<el-form-item label="名称">
					<el-input v-model="currentSellerForm.name" />
				</el-form-item>

				<el-form-item label="全称">
					<el-input v-model="currentSellerForm.fullname" />
				</el-form-item>
				<el-form-item label="Logo">
					<el-input v-model="currentSellerForm.logo" />
					<br>
					<img v-lazy="currentSellerForm.logo" alt="" style="border: 1px solid  #ddd;" width="120px" height="120px">
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
		name: "SellerList",
		data(){
			return{
				tableLoading:true,
				tableData:[],
				updateSellerDialogVisible:false,		//Dialog对话框
				currentSellerForm:{},				//当前编辑的seller对象
			}
		},
		methods:{
			initTableData(){
				this.$getRequest("/seller/all").then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.tableData.splice(0);
						this.tableData.push(...res.data.obj);
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
				this.updateSellerDialogVisible = true;
				this.currentSellerForm = row;
			},
			handleDelete(index, row) {
				this.$confirm('此操作将永久删除, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$getRequest("/route/delete?id="+row.id).then(res =>{
						if(res.data.status === 200){
							this.tableData.splice(index,1);
							this.$message.success("删除成功！")
						}
					})
				}).catch(() => {
					this.$message.info('已取消删除');
				});
			},

			handleSubmitDialog(){
				this.$postRequest("/seller/update",this.currentSellerForm).then(res =>{
					//console.log(res);
					if(res.data.status === 200){
						this.$message.success("更新成功！")
						this.updateSellerDialogVisible = false;
					}
				})
			},
		},
		created() {
			this.initTableData();
		}
	}
</script>

<style scoped>
	.seller-list-view{
		margin: 10px auto;
		width: 95%;
	}
</style>
