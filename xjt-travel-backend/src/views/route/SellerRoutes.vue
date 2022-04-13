<template>
	<div class="seller-routes-view">
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
						width="80"
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
						width="120"
						label="上架时间">
					<template slot-scope="scope">
						{{scope.row.publishTime |filterFormatDate(that) }}
					</template>
				</el-table-column>

				<el-table-column label="操作" width="150">
					<template slot-scope="scope">
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
	</div>
</template>

<script>
	export default {
		name: "SellerRoutes",
		data(){
			return{
				seller_id:"",
				tableLoading:true,
				tableData:[],
			}
		},
		methods:{
			initTableData(){
				this.$getRequest("/route/allRouteBySellerId?seller_id="+this.seller_id).then(res =>{
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
				console.log(row);
				// this.updateSellerDialogVisible = true;
				// this.currentSellerForm = row;
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
			tableColumnSwitch(index, row){
				console.log(row);
				this.$getRequest("/route/toggle/flag?id="+row.id).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.$message.success("更新成功！");
					}
				})
			},
		},
		created() {
			console.log(this.$route);
			this.seller_id = this.$route.query.seller_id;

			this.initTableData();
		}
	}
</script>

<style scoped>
	.seller-routes-view{
		margin: 10px auto;
		width: 95%;
	}

	.route-list{
		margin-top: 10px;
	}
</style>
