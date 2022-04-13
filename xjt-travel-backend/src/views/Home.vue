<template>
	<div class="home-view">
		<div class="dashboard-editor-container">
			<github-corner class="github-corner"/>

			<panel-group :panel-data="panelGroupData" />

			<el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
				<my-line-chart/>
			</el-row>

			<el-row :gutter="32">
				<el-col :xs="24" :sm="24" :lg="12">
					<div class="chart-wrapper">
						<!--traveller创作最多的前12名（bar图）-->
						<my-bar-chart/>
					</div>
				</el-col>

				<el-col :xs="24" :sm="24" :lg="12">
					<div class="chart-wrapper">
						<!--用户性别占比（饼图）-->
						<my-pie-chart/>
					</div>
				</el-col>
			</el-row>

			<el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
				<!--词云图-->
				<my-word-cloud/>
			</el-row>
		</div>
	</div>
</template>

<script>
	import GithubCorner from '@/components/GithubCorner'
	import PanelGroup from './dashboard/PanelGroup'

	import MyLineChart from './dashboard/MyLineChart'
	import MyPieChart from './dashboard/MyPieChart'
	import MyBarChart from './dashboard/MyBarChart'
	import MyWordCloud from './dashboard/MyWordCloud'

	export default {
		name: "Home",
		components: {
			GithubCorner,
			PanelGroup,

      MyLineChart,
			MyPieChart,
			MyBarChart,
			MyWordCloud,
		},
		data() {
			return {
				panelGroupData :{
					"usersCount":0,
					"travelNoteCount":0,
					"routeCount":0,
					"cartCount":0,
				},
			}
		},
		methods: {
			initPanelGroup(){
				//用户数量
				this.$getRequest("/user/all").then(res =>{
					//console.log(res);
					if(res.data.status === 200){
						let arr = res.data.obj;
						this.panelGroupData['usersCount'] = arr.length;
					}
				})
				//游记数量
				this.$getRequest("/travelNote/all/num").then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.panelGroupData['travelNoteCount'] = res.data.obj;
					}
				})
				//路线数量
				this.$getRequest("/route/all/num").then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.panelGroupData['routeCount'] = res.data.obj;
					}
				})
				//购物车
				this.$getRequest("/cart/all/num").then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.panelGroupData['cartCount'] = res.data.obj;
					}
				})
			},
		},
		created() {
			this.initPanelGroup();
		}
	}
</script>

<style lang="less" scoped>
	.dashboard-editor-container {
		padding: 32px;
		background-color: rgb(240, 242, 245);
		position: relative;
		.github-corner {
			position: absolute;
			top: 0;
			border: 0;
			right: 0;

		}
		.chart-wrapper {
			background: #fff;
			padding: 16px 16px 0;
			margin-bottom: 32px;
		}
	}
</style>
