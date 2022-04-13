<template>
	<div class="note-detail-view">
		<div class="title_bg">
			<el-image
								style="width: 100%; height: 100%"
								:src="noteDetail.cover_image"
								fit="none"></el-image>
		</div>
		<div class="content-wrapper">
			<div class="base-info">
				<div class="left person-avatar">
					<el-image
							style="width: 100%; height: 100%;border-radius: 50%;"
							:src="noteDetail.publiser_avatar"
							fit="cover"></el-image>
				</div>
				<div class="right info">
					<div class="title">{{noteDetail.title}}</div>
					<div class="publiser-time">
						<span class="publiser">{{noteDetail.publiser_name}}</span>
						<span class="time">{{noteDetail.publish_time}}</span>
					</div>
				</div>
			</div>
			<div class="travel-dir-list">
				<div class="start-time"><i class="el-icon-time"></i> 出发时间 / {{noteDetail.start_time}}</div>
				<div class="cost"><i class="el-icon-money"></i> 人均费用 / {{noteDetail.cost}}</div>
				<div class="day"><i class="el-icon-date"></i> 出行天数 / {{noteDetail.day}}</div>
			</div>

			<div class="article-wrapper" v-html="noteDetail.content"></div>
		</div>
	</div>
</template>

<script>
	export default {
		name: "NoteDetail",
		components:{
		},
		data(){
			return{
				//游记详情及作者信息
				noteDetail: {
          content: "",
          cost: "",
          cover_image: "",
          day: "",
          publiser_avatar: "",
          publiser_name: "",
          publish_time: "",
          start_time: "",
          title: "",
          tn_id: "",
          id:"",
        },
			}
		},
		methods:{
			//初始化数据
			initNoteAndAuthorInfo(){
				this.$getRequest("/travelNote/detail?tn_id="+this.noteDetail.id).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.noteDetail = res.data.obj;
					}
				})
			},
		},
		created() {
			console.log(this.$route);
			this.noteDetail.id = this.$route.params.id;

			this.initNoteAndAuthorInfo();
		},
	}
</script>

<style lang="less" scoped>
	.note-detail-view{
		width: 100%;
		.title_bg{
			width: 100%;
			height: 360px;
		}
		.content-wrapper{
			width: 80%;
			.base-info{
				display: flex;
				position: relative;
				margin: -80px 0 0 20%;
				.left{
					width: 160px;
					height: 160px;
				}
				.right{
					flex: 1;
					line-height: 80px;
					margin-left: 30px;
					.title{
						font-size: 32px;
						font-weight: bold;
						color: white;
					}
					.publiser-time{
						.time{
							margin-left: 50px;
						}
					}
				}
			}
			.travel-dir-list{
				display: flex;
				padding: 20px;
				border: 1px dashed #ddd;
				margin-left: 20%;
				margin-top: 20px;
				div{
					margin-left: 20px;
				}
			}
		}
	}
</style>
