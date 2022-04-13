<template>
  <div :class="className" :style="{height:height,width:width}"/>
</template>

<script>
  require('echarts/theme/macarons')     // echarts theme

  export default {
    name: "MyLineChart",
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
        pieChartData: [],
        lineChartData: [],

      }
    },
    watch: {
      pieChartData: {
        deep: true,
        handler(val) {
          this.setOptions(val)
        }
      },
      lineChartData: {
        deep: true,
        handler(val) {
          this.setOptions(val)
        }
      },
    },
    mounted() {
      this.$nextTick(() => {
        this.initChart()
      })
    },
    beforeDestroy() {
      if (!this.chart) {
        return
      }
      this.chart.dispose()
      this.chart = null
    },
    methods: {
      initChartData(){
        this.$getRequest("/route/getNumByCategory").then(res =>{
          console.log(res);
          if(res.data.status === 200){
            this.pieChartData.push(...res.data.obj);
          }
        })

        this.$getRequest("/route/getNumBySeller").then(res =>{
          console.log(res);
          if(res.data.status === 200){
            this.lineChartData.push(...res.data.obj)
          }
        })
      },

      initChart() {
        this.chart = this.$echarts.init(this.$el, 'macarons')

        this.setOptions(this.chartData)
      },
      setOptions(lineChartData) {
        let option = {
          title: [
            {
              left: 'center',
              text: '旅游路线分类统计'
            },
            {
              top: '52%',
              left: 'center',
              text: '各个旅行社的旅游路线'
            }
          ],
          tooltip: {
            show: true,
            feature: {
              mark: { show: true },
              dataView: { show: true, readOnly: false },
              restore: { show: true },
              saveAsImage: { show: true }
            }
          },
          xAxis: [
            {},
            {
              type: 'category',
              gridIndex: 1,
              data: this.lineChartData.map((item,index) =>{
                return item.name;
              })
            }
          ],
          yAxis: [
            {
              gridIndex: 0
            },
            {
              gridIndex: 1,
              type: 'value'
            }
          ],
          dataZoom: [
            {
              show: true,
              realtime: true,
              start: 0,
              end: 50,
              xAxisIndex: [0, 1]
            }
          ],
          grid: [
            {
              bottom: '55%'
            },
            {
              top: '60%'
            }
          ],
          series: [
            {
              name: '旅游路线分类',
              type: 'pie',
              radius: [30, 100],
              center: ['50%', '25%'],
              legend: {
                top: 'bottom'
              },
              roseType: 'area',
              itemStyle: {
                borderRadius: 8
              },
              data: this.pieChartData,
            },
            {
              type: 'line',
              smooth: true,
              showSymbol: false,
              data: this.lineChartData.map((item,index) =>{
                return item.num;
              }),
              xAxisIndex: 1,
              yAxisIndex: 1
            }
          ]
        };

        this.chart.setOption(option)
      }
    },
    created() {
      this.initChartData();
    }
  }
</script>

<style scoped>

</style>
