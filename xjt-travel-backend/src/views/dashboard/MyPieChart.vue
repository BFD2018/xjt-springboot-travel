<template>
	<div :class="className" :style="{height:height,width:width}" />
</template>

<script>
	require('echarts/theme/macarons') // echarts theme

	export default {
		name: "MyPieChart",
		props: {
			className: {
				type: String,
				default: 'chart'
			},
			width: {
				type: String,
				default: '100%'
			},
			height: {
				type: String,
				default: '300px'
			}
		},
		data() {
			return {
				chart: null,
				chartData: [
					{name:"男",value:0},
					{name:"女",value:0},
				]
			}
		},
		mounted() {
			this.initChart();
		},
		watch: {
			chartData: {
				deep: true,
				handler(val) {
					this.chartSetOption(val)
				}
			}
		},
		beforeDestroy() {
			if (!this.chart) {
				return
			}
			this.chart.dispose()
			this.chart = null
		},
		methods: {
			initChart() {
				this.chart = this.$echarts.init(this.$el, 'macarons')

				this.chartSetOption(this.chartData);
			},

			chartSetOption(chartData){
				let option = {
					title: {
						text: '用户性别占比',
						left: 'center'
					},
					tooltip: {
						trigger: 'item'
					},
					legend: {
						orient: 'vertical',
						left: 'left'
					},
					series: [
						{
							name: 'AccessFrom',
							type: 'pie',
							radius: '50%',
							data: chartData,
						}
					]
				};
				this.chart.setOption(option);
			},

			initChartData(){
				this.$getRequest("/user/all").then(res =>{
					console.log(res);
					if(res.data.status === 200){
						res.data.obj.forEach((item,index)=>{
							if(item.sex == 1){
								this.chartData[0]["value"]++;
							}else{
								this.chartData[1]["value"]++;
							}
						})
					}
				})
			},
		},
		created() {
			this.initChartData();
		}
	}
</script>
