import { generateService } from '@umijs/openapi'

generateService({
  requestLibPath: "import request from '@/request'",
  schemaPath: 'http://localhost:8126/linkapi/v2/api-docs',
  serversPath: './src',
})
