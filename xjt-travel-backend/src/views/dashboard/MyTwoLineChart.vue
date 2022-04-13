<template>
	<div :class="className" ref="chart" :style="{height:height,width:width}"/>
</template>

<script>
	require('echarts/theme/roma') 			//echarts theme
	import { debounce } from '@/utils/index.js'

	export default {
		name:"MyTwoLineChart",
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
				default: '700px'
			}
		},
		data() {
			return {
				chart: null,
				chartData:[],
			}
		},
		mounted() {
			this.$nextTick(() => {
				this.initChart()
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
				this.chart = this.$echarts.init(this.$refs.chart,"roma");

				this.chartSetOption(this.chartData);
			},
			chartSetOption(arr){
				let type1_locationList = [];
				let type1_valueList = [];
				let type2_locationList = [];
				let type2_valueList = [];

				this.randomList(arr).forEach((item,index) =>{
          console.log(item);
          if(item.type === true){     //热门游记
						type1_locationList.push(item.location);
						type1_valueList.push(item.num);
					}else{    //最新发布
						type2_locationList.push(item.location);
						type2_valueList.push(item.num);
					}
				})

				let option = {
					// Make gradient line here
					visualMap: [
						{
							show: false,
							type: 'continuous',
							seriesIndex: 0,
							min: 0,
							max: 400
						},
						{
							show: false,
							type: 'continuous',
							seriesIndex: 1,
							dimension: 0,
							min: 0,
							max: type2_valueList.length - 1
						}
					],
					title: [
						{
							left: 'center',
							text: '热门游记Top12'
						},
						{
							top: '55%',
							left: 'center',
							text: '最新发布Top12'
						}
					],
					tooltip: {
						trigger: 'axis'
					},
					xAxis: [
						{
							data: type1_locationList.slice(0,12)
						},
						{
							data: type2_locationList.slice(0,12),
							gridIndex: 1
						}
					],
					yAxis: [
						{},
						{
							gridIndex: 1
						}
					],
					grid: [
						{
							bottom: '60%'
						},
						{
							top: '60%'
						}
					],
					series: [
						{
							type: 'line',
							showSymbol: false,
							smooth: true,
							color: [
								'#dd6b66',
								'#759aa0',
								'#e69d87',
								'#8dc1a9',
								'#ea7e53',
								'#eedd78',
								'#73a373',
								'#73b9bc',
								'#7289ab',
								'#91ca8c',
								'#f49f42'
							],
							data: type2_valueList.slice(0,12)
						},
						{
							type: 'line',
							showSymbol: false,
							data: type2_valueList.slice(0,12),
							smooth: true,
							color: [
								'#dd6b66',
								'#759aa0',
								'#e69d87',
								'#8dc1a9',
								'#ea7e53',
								'#eedd78',
								'#73a373',
								'#73b9bc',
								'#7289ab',
								'#91ca8c',
								'#f49f42'
							],
							xAxisIndex: 1,
							yAxisIndex: 1
						}
					]
				};

				this.chart.setOption(option)
			},

			randomList(arr){
				arr.sort(function() {
					return Math.random() > 0.5    // 随机返回正或负值 达到排序的目的
				});
				return arr
			},

			initChartData(){
				this.$getRequest("/travelNote/byTypeLocation").then(res =>{
					console.log(res);
					if(res.data.status === 200){
						this.chartData.push(...res.data.obj);
					}
				});
			},
		},
		created() {
			this.initChartData();
		}
	}
</script>
