import { createUsingPost } from './shortLinkController'

export async function createShortLink(url: string) {
  const res = await createUsingPost({ url })
  if (!res || res.code !== 0 || !res.data) {
    throw new Error(res?.message || '生成短链接失败')
  }
  return res.data
}

export function buildVisitPath(shortCode: string) {
  return `/linkapi/visit/${encodeURIComponent(shortCode)}`
}
