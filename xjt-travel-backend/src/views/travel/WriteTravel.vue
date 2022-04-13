<template>
	<div class="write-travelnote-view">
		<div class="my-title-text-clip">{{editForm.id?"编辑游记":"新增游记"}}</div>

    <el-form ref="editForm"
						 class="editForm"
						 status-icon
						 label-width="100px"
						 label-position="left"
						 :model="editForm">
			<el-form-item label="ID" v-if="editForm.id">
				<el-input v-model="editForm.id" disabled></el-input>
			</el-form-item>

			<el-form-item label="标题"
										:rules="[
											{ required: true, message: '请输入游记标题', trigger: 'blur' },
										]"
										prop="title">
				<el-input v-model="editForm.title"></el-input>
			</el-form-item>

      <el-form-item label="封面图">
        <el-input v-model="editForm.coverImage"></el-input>
        <br>
        <img v-lazy="editForm.coverImage" alt="" width="200px" height="200px">
      </el-form-item>

			<el-form-item label="描述">
        <el-input
          type="textarea"
          :rows="4"
          placeholder="请输入描述内容"
          v-model="editForm.description">
        </el-input>
			</el-form-item>

			<el-form-item label="目的地">
				<el-input v-model="editForm.location"></el-input>
			</el-form-item>

			<el-form-item label="类型">
				<el-switch
						style="display: block"
						v-model="editForm.type"
						active-color="#13ce66"
						inactive-color="#ff4949"
						active-text="热门游记"
						inactive-text="最新发布">
				</el-switch>
			</el-form-item>

			<el-form-item label="发布者名字">
				<el-input v-model="editForm.publiser_name"></el-input>
			</el-form-item>

			<el-form-item label="发布者头像">
				<el-input v-model="editForm.publiser_avatar"></el-input>
				<br>
				<img v-lazy="editForm.publiser_avatar" alt="" width="200px" height="200px">
			</el-form-item>

			<el-form-item label="发布时间">
				<el-input v-model="editForm.publish_time"></el-input>
			</el-form-item>

			<el-form-item label="出发时间">
				<el-input v-model="editForm.start_time"></el-input>
			</el-form-item>

			<el-form-item label="天数">
				<el-input v-model="editForm.day"></el-input>
			</el-form-item>

			<el-form-item label="费用">
				<el-input v-model="editForm.cost"></el-input>
			</el-form-item>

			<el-form-item label="游记内容" prop="content" class="mavon-editor">
        <div id="wangeditor">
          <p class="warn-content">
            富文本基于
            <el-link type="primary" href="https://www.kancloud.cn/wangfupeng/wangeditor3/332599" target="_blank">wangEditor</el-link>
          </p>
        </div>

        <el-row type="flex" justify="space-between" style="margin-top: 20px;">
          <el-col push=23>
            <el-button type="success" @click="previewEditorContent">预览</el-button>
          </el-col>
        </el-row>
			</el-form-item>

			<el-form-item style="margin: auto;">
				<el-button type="info" @click="reset">重置</el-button>
				<el-button type="primary" @click="submitForm">提交</el-button>
			</el-form-item>

			<div style="height: 30px;"></div>
		</el-form>

    <el-dialog title="预览编辑内容" :visible.sync="dialogTableVisible">
      <div id="editor-content" v-html="editorContent"></div>
    </el-dialog>
	</div>
</template>

<script>
  import E from "wangeditor"

	export default {
		name: "WriteTravel",
		components: {},
		data() {
			return {
				editForm: {
					id:"",
					title: "",
					description: "",
					coverImage: "",
					location:"",
					type: true,			//类型,true-热门游记  false-最新发布
					content: "",
					viewCount:0,
				},

        /*wangEditor*/
        editor:null,
        dialogTableVisible:false,
        editorContent:"",
			}
		},
		methods: {
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

			//根据id请求游记详细内容
			initTravelNoteDetailData(tn_id){
				this.$getRequest("/travelNote/detail?tn_id="+tn_id).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.editForm.publiser_avatar = res.data.obj.publiser_avatar;
						this.editForm.publiser_name = res.data.obj.publiser_name;
						this.editForm.publish_time = res.data.obj.publish_time;
						this.editForm.start_time = res.data.obj.start_time;
						this.editForm.day = res.data.obj.day;
						this.editForm.cost = res.data.obj.cost;
						this.editForm.content = res.data.obj.content;
						//设置wangeditor内容
            this.editor.txt.html(this.editForm.content)
					}else{
						this.$message.error(res.data.msg);
					}
				})
			},

      initWangEditor(){
        this.editor = new E("#wangeditor")

        this.editor.config.zIndex = 100

        // 设置编辑区域高度为 500px
        this.editor.config.height = 500
        // 注意，先配置 height ，再执行 create()
        this.editor.create();

        //设置内容
        if(this.editForm.content){
          this.editor.txt.html(this.editForm.content)
        }

      },

      previewEditorContent(){
        this.dialogTableVisible = true;
        this.editorContent = this.editor.txt.html();
      },

		},
		created() {
			console.log(this.$route);

			if(this.$route.params.id){
        this.editForm = this.$route.params;
        this.initTravelNoteDetailData(this.$route.params.id);
      }
		},
    mounted() {
      this.$nextTick(() =>{
        this.initWangEditor();
      });
    }
	}
</script>

<style lang="less" scoped>
	.write-travelnote-view{
		margin: 100px auto;
		width: 95%;
    overflow: hidden;
    padding: 10px 20px;
		background-color: #f5f5f5;
		position: relative;
		.editForm{
			position: relative;
			width: 100%;
			.mavon-editor{
				width: 100%;
			}
		}
	}
</style>
