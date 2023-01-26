import request from '@/utils/request'

// 创建hq用户
export function createCustomer(data) {
  return request({
    url: '/hq/customer/create',
    method: 'post',
    data: data
  })
}

// 更新hq用户
export function updateCustomer(data) {
  return request({
    url: '/hq/customer/update',
    method: 'put',
    data: data
  })
}

// 删除hq用户
export function deleteCustomer(id) {
  return request({
    url: '/hq/customer/delete?id=' + id,
    method: 'delete'
  })
}

// 获得hq用户
export function getCustomer(id) {
  return request({
    url: '/hq/customer/get?id=' + id,
    method: 'get'
  })
}

// 获得hq用户分页
export function getCustomerPage(query) {
  return request({
    url: '/hq/customer/page',
    method: 'get',
    params: query
  })
}

// 导出hq用户 Excel
export function exportCustomerExcel(query) {
  return request({
    url: '/hq/customer/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}

  // 获得订单金额统计
export function getAllOrderSum(query) {
  return request({
    url: '/hq/customer/allStatistics',
    method: 'get',
    params: query,
  })
}

