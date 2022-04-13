<template>
	<div class="add-traveller-article-view">
		<div class="my-title-text-clip">{{isEditArticle?"编辑文章":"新增文章"}}</div>

		<el-form ref="editForm"
						 class="editForm"
						 status-icon
						 label-width="100px"
						 label-position="left"
						 :model="editForm">
			<el-form-item label="ID" v-if="isEditArticle">
				<el-input v-model="editForm.id" disabled></el-input>
			</el-form-item>

			<el-form-item label="标题"
										:rules="[
											{ required: true, message: '请输入游记标题', trigger: 'blur' },
										]"
										prop="title">
				<el-input v-model="editForm.title"></el-input>
			</el-form-item>

			<el-form-item label="描述"
										:rules="[
											{ required: true, message: '请为游记输入简单描述', trigger: 'blur' },
										]"
										prop="description">
				<el-input v-model="editForm.description"></el-input>
			</el-form-item>

			<el-form-item label="封面图">
				<el-input v-model="editForm.thumbnail"></el-input>
				<br>
				<img v-lazy="editForm.thumbnail" class="my-img-border1" alt="" width="200px" height="200px">
			</el-form-item>

			<el-form-item label="旅行家">
				<el-descriptions title="基本信息" border :column=2 v-if="isEditArticle">
					<el-descriptions-item label="作者id">{{editForm.writer_id}}</el-descriptions-item>
					<el-descriptions-item label="作者名">{{editForm.author_name}}</el-descriptions-item>
					<el-descriptions-item label="作者头像">
						<img v-lazy="editForm.author_avatar" class="my-img-border1" alt="" width="150px" height="150px">
					</el-descriptions-item>
					<el-descriptions-item label="作者描述信息">{{editForm.author_description}}</el-descriptions-item>
				</el-descriptions>

				<el-input v-model="editForm.writer_id" placeholder="旅行家id" v-else></el-input>
			</el-form-item>

			<el-form-item label="文章浏览量">
				<el-input-number v-model="editForm.view" :min="0"></el-input-number>
			</el-form-item>

			<el-form-item label="文章内容" prop="content" class="mavon-editor">
				<mavon-editor
						ref="md"
						placeholder="请输入文章内容..."
						:boxShadow="false"
						style="z-index:1;border: 1px solid #d9d9d9;height:400px;"
						v-model="editForm.detail_content"/>
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
		name: "AddArticel",
		data(){
			return{
				isEditArticle:true,
				editForm: {
					id:"",
					title: "",
					description: "",
					thumbnail: "",
					writer_id:"",
					author_name:"",
					author_avatar:"",
					author_description:"",
					view:0,
					detail_content: "",
				},
			}
		},
		methods:{
			//提交表单
			submitForm() {
				console.log(this.editForm);
				// this.$refs.editForm.validate((valid) => {
				// 	if (valid) {
				// 		this.$postRequest("/blog/save",this.editForm).then(res =>{
				// 			if(res.data.status ===200){
				// 				this.$notify.success(res.data.msg);
				// 				this.$router.push('/admin/allblog')
				// 			}
				// 		})
				// 	} else {
				// 		this.$notify.error('error submit!!');
				// 		return false;
				// 	}
				// });
			},
			//重置（清空输入）
			reset(){
				this.$refs.editForm.resetFields();
			},

			//根据id请求游记详情内容
			initArticleDetailData(article_id){
				this.$getRequest("/traveller/note/article/byId?id="+article_id).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.editForm.detail_content = res.data.obj.content;
					}else{
						this.$message.error(res.data.msg);
					}
				})
			},
		},
		mounted() {
			this.editForm = {}
			console.log(this.$route);

			if(this.$route.params.id == "" || this.$route.params.id == undefined){
				this.isEditArticle = false;		//新增文章
				this.editForm.writer_id = this.$route.query.writer_id;
			}else{
				//编辑文章
				this.isEditArticle = true
				this.editForm = this.$route.params;
				this.initArticleDetailData(this.$route.params.id);
			}
		},
		created() {

		},
		beforeDestroy() {
			this.editForm = {};
		}
	}
</script>

<style lang="less" scoped>
	.add-traveller-article-view{
		margin: 100px auto;
		width: 98%;
		position: relative;
		.editForm{
			border: 1px dashed #eee;
			padding: 10px;
			position: relative;
			width: 1400px;
			.mavon-editor{
				width: 100%;
			}
		}
	}
</style>
