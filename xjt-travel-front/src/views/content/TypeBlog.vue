<template>
	<div class="type-blog-view">
		<el-select v-model="type_id" placeholder="请选择" @change="selectChange">
			<el-option
					v-for="(item,index) in typesList"
					:key="index"
					:label="item.name"
					:value="item.id">
			</el-option>
		</el-select>

		<div class="blog-content my-marginTop20">
			<div class="main-content">
				<div v-for="(item,index) in blogsList" :key="index" class="blog-item">
					<BlogComp :blogInfo="item"></BlogComp>
				</div>
			</div>

			<!--分页-->
			<div style="margin-top: 20px;" class="my-border-padding">
				<el-pagination
						background
						@size-change="handleSizeChange"
						@current-change="handleCurrentChange"
						:current-page="currentPage"
						:page-sizes="pageSizes"
						:page-size="pageSize"
						layout="total, sizes, prev, pager, next, jumper"
						:total="total">
				</el-pagination>
			</div>
		</div>
	</div>
</template>

<script>
	import BlogComp from "./BlogComp";

	export default {
		name: "TypeBlog",
		components:{
			BlogComp,
		},
		data(){
			return{
				typesList:[],
				blogsList:[],
				type_id:null,		//类型id

				//分页
				total:0,
				currentPage: 1,
				pageSize: 6,
				pageSizes: [3, 6, 9, 12],
			}
		},
		methods:{
			_getTypes(){
				//获取所有Type
				this.$getRequest("/type/all").then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.typesList.push(...res.data.obj)
						this.type_id = res.data.obj[0].id;
					}
				})
			},

			_getBlogsByTypeId(){
				//获取所有type对应的blog
				let params = {
					"current":this.currentPage,
					"size":this.pageSize,
					"type_id":this.type_id,
				}
				this.$getRequest("/blog/getByPageHelper?"+this.$qs.stringify(params)).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.blogsList.splice(0);
						this.blogsList.push(...res.data.obj.list);
						this.total = parseInt(res.data.obj.total);
					}else{
						this.$message.error(res.data.msg);
					}
				})
			},

			selectChange(val){
				console.log("======当前选中的item.value=======");
				this.type_id = val;
				this._getBlogsByTypeId();
			},

			/*分页*/
			handleSizeChange(pageSize){
				console.log(`每页 ${pageSize} 条`);
				this.pagesize = pageSize
				this._getBlogsByTypeId()
			},
			handleCurrentChange(current){
				console.log(`当前页: ${current}`);
				this.currentPage = current
				this._getBlogsByTypeId()
			},
		},
		created() {
			this._getTypes();

			setTimeout(() =>{
				this._getBlogsByTypeId();
			},1000);
		}
	}
</script>

<style scoped>

</style>