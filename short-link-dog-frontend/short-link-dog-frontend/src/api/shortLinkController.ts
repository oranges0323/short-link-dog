// @ts-ignore
/* eslint-disable */
import request from '@/request'

/** create POST /linkapi/create */
export async function createUsingPost(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.createUsingPOSTParams,
  options?: { [key: string]: any }
) {
  return request<API.BaseResponseString_>('/linkapi/create', {
    method: 'POST',
    params: {
      ...params,
    },
    ...(options || {}),
  })
}

/** visit GET /linkapi/visit/${param0} */
export async function visitUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.visitUsingGETParams,
  options?: { [key: string]: any }
) {
  const { shortLink: param0, ...queryParams } = params
  return request<any>(`/linkapi/visit/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  })
}
