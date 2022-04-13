<template>
	<div class="traveller-item">
		<div class="thumbnail">
			<el-image
					style="width: 100%; height: 180px;"
					:src="article.thumbnail"
					fit="cover"></el-image>
		</div>
		<div class="desc">
			<div class="title hvr-underline-reveal">{{article.title}}</div>
			<div class="desc">{{article.description}}</div>
		</div>
	</div>
</template>

<script>
	export default {
		name: "TravellerItem",
		props:{
			article:{
				type:Object,
				default(){
					return{}
				}
			}
		}
	}
</script>

<style lang="less" scoped>
	.traveller-item{
		padding: 20px 10px;
		border-bottom: 1px dashed #ddd;

		.thumbnail{
			width: 100%;
		}
		.desc {
			.title {
				margin: 10px 0 5px;
				font-size: 22px;
				width: 100%;
				color: #333;
				font-weight: normal;
				height: 36px;
				line-height: 36px;
				overflow: hidden;
				white-space: nowrap;
				text-overflow: ellipsis;
			}

			.desc {
				height: 60px;
				line-height: 20px;
				font-size: 14px;
				overflow: hidden;
			}
		}
	}
</style>