<template>
	<div class="xjt-tags-view">
		<h1>我的标签</h1>
		<h2>目前共计 <span class="hvr-border-fade" style="padding: 8px">{{tagsList.length}}</span> 个标签</h2>

		<div class="my-badges-grid">
			<div v-for="(item,index) in tagsList" :key="index" class="my-badge">
				<el-badge :value="item.blog_num" class="item">
					<el-button size="small" type="success" plain>{{item.tag_name}}</el-button>
				</el-badge>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		name: "TagBlog",
		data(){
			return{
				tagsList:[]
			}
		},
		methods:{
			_getAllTags(){
				this.$getRequest("/tag/allTagToBlogNum").then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.tagsList.splice(0);
						this.tagsList.push(...res.data.obj);
					}
				})
			}
		},
		created() {
			this._getAllTags();
		}
	}
</script>

<style lang="less" scoped>
	.xjt-tags-view{
		margin: auto;
		text-align: center;
		.my-badges-grid{
			margin-top: 20px;
			display: grid;
			grid-template-columns: repeat(6,1fr);
			grid-column-gap: 20px;
			grid-row-gap: 20px;
			.my-badge{
				border: 1px solid #ddd;
				height: 60px;
				align-items: center;
				line-height: 60px;
			}
		}
	}
</style>