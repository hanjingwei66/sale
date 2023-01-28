<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'
import {
  listAllUserNames
} from "@/api/system/user";
import { getAllOrderSum } from  "@/api/hq/customer";
const animationDuration = 6000

export default {
  mixins: [resize],
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
      xdata: [],
      ydata: [],
      query: {
        status: 0
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.getAllUserNames()
      this.getAllOrderSum(this.query)
      this.initChart()
    })
  },
  created() {

  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    getAllOrderSum(query){
      getAllOrderSum(query).then(response =>{
        console.log(response)
      })
    },
    getAllUserNames(){
      listAllUserNames().then(response => {
        this.chart.setOption({
          xAxis: [{
            data:  response.data.nickName,
          }],
          series: [{
            name: '预期',
            type: 'bar',
            // stack: 'vistors',
            barWidth: '10%',
            data: response.data.expect,
            animationDuration
          },{
            name: '实际',
            type: 'bar',
            // stack: 'vistors',
            barWidth: '10%',
            data: response.data.actual,
            animationDuration
          }]
        })
      })
    },
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')

      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        grid: {
          top: 10,
          left: '2%',
          right: '2%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [{
          type: 'category',
          data: this.xdata,
          axisTick: {
            alignWithLabel: true
          }
        }],
        yAxis: [{
          type: 'value',
          axisTick: {
            show: false
          }
        }],
        legend: {
          // orient: 'vertical',
          data: ['预期', '实际']
        },
        series: [{
          name: '预期',
          type: 'bar',
          // stack: 'vistors',
          barWidth: '10%',
          data: [79, 52, 200, 334, 390, 330, 220,79, 52, 200, 334, 390, 330, 220],
          animationDuration
        }, {
          name: '实际',
          type: 'bar',
          // stack: 'vistors',
          barWidth: '10%',
          data: [80, 52, 200, 334, 390, 330, 220,80, 52, 200, 334, 390, 330, 220],
          animationDuration
        }]
      })
    }
  }
}
</script>
