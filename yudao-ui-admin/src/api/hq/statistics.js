import request from '@/utils/request'

export function statisticsCustomer(data) {
  return request({
    url: '/hq/customer/statistics',
    method: 'post',
    data: data
  })
}
