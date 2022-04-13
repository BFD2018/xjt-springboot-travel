<template>
	<div class="travel-note">
		<div class="layout-left">
			<el-image class="hvr-grow"
					style="width: 100%; height: 100%;"
					:src="travel.coverImage"
					fit="fill"></el-image>
		</div>
		<div class="layout-right">
			<div class="travel-title hvr-underline-from-center"
					 @click="$router.push('/travelnote/'+travel.id)">{{travel.title}}</div>
			<div class="travel-desc">{{travel.description}}</div>
			<div class="travel-extra">
				<div class="location"><i class="el-icon-location"></i> {{travel.location}}</div>
				<div class="viewCount"><i class="el-icon-view"></i> {{travel.viewCount}}</div>
				<div class="up">
					<el-badge :value="travel.up" class="item">
						<el-button size="mini" type="success" @click="upClickHandle(travel)">顶</el-button>
					</el-badge>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		name: "TravelNote",
		props:{
			travel:{
				type:Object,
				default(){
					return{}
				}
			}
		},
		methods:{
			upClickHandle(item){
				console.log(item);
				this.$getRequest("/travelNote/up/add?id="+item.id).then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.$message.success("点赞ok！")
						this.travel.up++;
					}
				})
			}
		},
	}
</script>

<style lang="less" scoped>
	.travel-note{
		padding: 15px;
		border: 1px solid #ddd;
		border-radius: 8px;
		margin-top: 10px;
		display: flex;
		.layout-left{
			width: 240px;
			height: 160px;
			overflow: hidden;
		}
		.layout-right{
			flex: 1;
			margin-left: 20px;
			.travel-title{
				font-size: 24px;
				line-height: 50px;
			}
			.travel-desc{
				font-size: 14px;
				line-height: 30px;
			}
			.travel-extra{
				display: flex;
				line-height: 40px;
				div{
					margin-left: 20px;
				}
				.up{
					height: 40px;
					line-height: 40px;
				}
			}
		}
	}

</style>