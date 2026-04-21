type RequestOptions = RequestInit & {
  params?: Record<string, unknown>
}

function buildUrl(path: string, params?: Record<string, unknown>) {
  const url = new URL(path, window.location.origin)
  if (params) {
    Object.entries(params).forEach(([key, value]) => {
      if (value !== undefined && value !== null) {
        url.searchParams.set(key, String(value))
      }
    })
  }
  return url.pathname + url.search
}

export default async function request<T>(path: string, options: RequestOptions = {}): Promise<T> {
  const { params, headers, ...rest } = options
  const response = await fetch(buildUrl(path, params), {
    credentials: 'include',
    headers: {
      'Content-Type': 'application/json',
      ...(headers || {}),
    },
    ...rest,
  })

  if (!response.ok) {
    throw new Error(`请求失败: ${response.status}`)
  }

  const contentType = response.headers.get('content-type') || ''
  if (contentType.includes('application/json')) {
    return (await response.json()) as T
  }

  return (await response.text()) as T
}
