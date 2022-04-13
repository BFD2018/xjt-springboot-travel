<template>
	<div class="blog-comp-view">
		<el-card class="blog-card my-marginTop10">
			<el-row type="flex" :gutter="30">
				<el-col style="width: 320px;height: 180px;border:1px solid #ddd;overflow: hidden;border-radius: 8px;padding: 10px;">
						<el-image
								@click="gotoBlogDetail"
								class="hvr-grow"
								style="width: 100%;height: 100%;"
								:src="blogInfo.first_picture"
								fit="cover"></el-image>
				</el-col>

				<el-col :span="18">
					<el-row>
						<!-- 根据shareStatement属性判断文章是否为草稿 -->
						<el-button size="mini" v-if="!blogInfo.share_statement"
											 type="warning" icon="el-icon-edit" circle></el-button>
							<span @click="gotoBlogDetail" class="my-medium-title">{{blogInfo.title}}</span>
					</el-row>

					<div style="margin-top: 15px;" class="my-flex">
						<div style="margin-right: 10px;" v-if="blogInfo.is_delete">
							<el-tag size="mini"   type="danger">已删除</el-tag>
						</div>
						<div style="margin-right: 10px;" v-if="!blogInfo.share_statement">
							<el-tag size="mini"   type="warning">草稿
							</el-tag>
						</div>
						<div style="margin-right: 10px;"  v-if="blogInfo.share_statement" >
							<el-tag size="mini">{{blogInfo.flag}}
							</el-tag>
						</div>
						<div style="margin-right: 20px;"  v-if="blogInfo.share_statement" >
							<el-tag size="mini" type="info">
								{{blogInfo.published == 'false' ? '私密' : '公开'}}
							</el-tag>
						</div>
						<div style="margin-right: 20px;"><i class="el-icon-view"> {{blogInfo.views}} </i>
						</div>
						<div style="margin-right: 20px;"><i class="el-icon-chat-square">
							{{blogInfo.comment_count}} </i>
						</div>

						<div class="my-flex-span1" style="margin-right: 20px;" ><i class="el-icon-date"> {{blogInfo.create_time | filterFormatDate(that)}}</i></div>
					</div>

					<el-row type="flex" class="my-marginTop20">
						<el-col style="width: 80px">
							<el-image
									class="my-border"
									style="width: 70px; height: 70px"
									:src="blogInfo.avatar"
									fit="contain"></el-image>
						</el-col>
						<el-col :span="1" style="line-height: 70px;align-items: center;font-size: 20px;">{{blogInfo.username}}</el-col>
					</el-row>
				</el-col>
			</el-row>
		</el-card>
	</div>
</template>

<script>
	export default {
		name: "BlogComp",
		props:{
			blogInfo:{
				type:Object,
				default(){
					return {};
				}
			}
		},
		methods:{
			gotoBlogDetail(){
				this.$router.push("/blog/detail?blog_id=" + this.blogInfo.bid);
			}
		},
	}
</script>

<style scoped>
</style>