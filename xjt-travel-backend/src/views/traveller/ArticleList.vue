<template>
	<div class="travellernote-list-view">
		<div class="travelnote-header">
			<!-- 搜索 -->
			<el-input
					v-model="queryKeyForTitle"
					clearable
					size="small"
					placeholder="按文章title模糊搜索"
					class="filter-item"/>

			<el-input
					v-model="queryKeyForAuthor"
					clearable
					size="small"
					placeholder="按作者Name搜索"
					class="filter-item"/>

			<div class="btn">
				<el-button @click="initTableData" type="success" size="medium" icon="el-icon-search">搜索</el-button>
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
						label="文章ID">
					<template slot-scope="scope">
						{{scope.row.id}}
					</template>
				</el-table-column>

				<el-table-column
						:show-overflow-tooltip="true"
						width="150"
						label="标题">
					<template slot-scope="scope">
						{{scope.row.title}}
					</template>
				</el-table-column>

				<el-table-column
						width="140"
						label="缩略图">
					<template slot-scope="scope">
						<img v-lazy="scope.row.thumbnail" alt="" style="border: 1px solid  #ddd;" width="120px" height="120px">
					</template>
				</el-table-column>

				<el-table-column
						label="描述">
					<template slot-scope="scope">
						{{scope.row.description}}
					</template>
				</el-table-column>

				<el-table-column
						width="150"
						label="作者信息">
					<template slot-scope="scope">
						<img v-lazy="scope.row.author_avatar" alt="" style="border: 1px solid  #ddd;" width="130px" height="130px">
						<div></div>
						<el-tag @click="$router.push('/traveller/articles/' + scope.row.writer_id)">{{scope.row.author_name}}</el-tag>
					</template>
				</el-table-column>

				<el-table-column label="操作" width="220">
					<template slot-scope="scope">
						<el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
						<el-button type="success" size="mini" @click="previewNoteContent(scope.row)">预览</el-button>
						<el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>

		<el-dialog title="预览文章" :visible.sync="dialogTableVisible"
							 @close="currentArticle = {}"
							 append-to-body
							 width="70%">
			<div class="previewNote" v-html="currentArticle">
			</div>
		</el-dialog>

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
	</div>
</template>

<script>
	export default {
		name: "ArticleList",
		data(){
			return{
				/*查询条件*/
				queryKeyForTitle:"",				//按Article标题搜索
				queryKeyForAuthor:"",			//按Article作者搜索

				tableLoading:true,
				tableData:[],

				dialogTableVisible:false,
				currentArticle:"",

				/*分页*/
				currentPage:1,
				pageSize:6,
				total:0
			}
		},
		methods:{
			initTableData(){
				let params = {
					"title":this.queryKeyForTitle,
					"author_name":this.queryKeyForAuthor,
					"currentPage":this.currentPage,
					"pageSize":this.pageSize
				}
				let baseUrl = "/traveller/note/queryByConditions?" + this.$qs.stringify(params,{encode:false});

				this.$getRequest(baseUrl).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.tableData.splice(0);
						this.tableData.push(...res.data.obj.list);
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
					name:"新增文章",
					params:row,
				})
			},
			handleDelete(index, row) {
				this.$confirm('此操作将永久删除, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$getRequest("/traveller/note/delete?id="+row.id).then(res =>{
						if(res.data.status === 200){
							this.tableData.splice(index,1);
							this.$message.success("删除成功！")
						}
					})
				}).catch(() => {
					this.$message.info('已取消删除');
				});
			},

			previewNoteContent(note) {
				this.$getRequest("/traveller/note/article/byId?id="+note.id).then(res =>{
					//console.log(res);
					if (res.data.status === 200) {
						this.currentArticle = res.data.obj.content
						this.dialogTableVisible = true;
					} else {
						this.$message.error(res.data.msg);
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
	.travellernote-list-view{
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
