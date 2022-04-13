<template>
	<div :class="className" :style="{height:height,width:width}" />
</template>

<script>
	require('echarts/theme/macarons') // echarts theme
	import { debounce } from '@/utils/index.js'

	const animationDuration = 4000

	export default {
		name:"MyBarChart",
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
			},
		},
		data() {
			return {
				chart: null,
				chartData:[],
			}
		},
		mounted() {
			this.$nextTick(() =>{
				this.initChart();
			})
			this.__resizeHandler = debounce(() => {
				if (this.chart) {
					this.chart.resize()
				}
			}, 100)
			window.addEventListener('resize', this.__resizeHandler)
		},
		beforeDestroy() {
			if (!this.chart) {
				return
			}
			window.removeEventListener('resize', this.__resizeHandler)
			this.chart.dispose()
			this.chart = null
		},
		watch: {
			chartData: {
				deep: true,
				handler(val) {
					this.chartSetOption(val)
				}
			}
		},
		methods: {
			initChart() {
				this.chart = this.$echarts.init(this.$el, 'macarons')

				this.chartSetOption(this.chartData);
			},
			chartSetOption(chartData){
				let option = {
					title: {
						text: '旅行家游记',
						subtext: '创作量最高产的旅行家Top'
					},
					tooltip: {
						trigger: 'axis',
						axisPointer: {
							type: 'shadow'
						}
					},
					xAxis: {
						type: 'category',
						axisLabel: {
							interval:0, //坐标刻度之间的显示间隔，默认就可以了（默认是不重叠）
							rotate:38   //调整数值改变倾斜的幅度（范围-90到90）
						},
						data: this.chartData.map((item) =>{return item.name})
					},
					yAxis: {
						type: 'value'
					},
					series: [
						{
							data: this.chartData.map((item) =>{return item.num}),
							type: 'bar'
						}
					]
				}
				this.chart.setOption(option);
			},

			initChartData(){
				this.$getRequest("/traveller/note/groupByTraveller?num=12").then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.chartData.push(...res.data.obj);
					}
				})
			},
		},
		created() {
			this.initChartData();
		}
	}
</script>
