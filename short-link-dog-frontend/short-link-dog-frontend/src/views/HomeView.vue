<script setup lang="ts">
import { ref } from 'vue'
import { createShortLink } from '@/api/shortLinkService'

const inputUrl = ref('')
const loading = ref(false)
const errorMessage = ref('')
const shortUrl = ref('')

const createShortLinkHandler = async () => {
  errorMessage.value = ''
  shortUrl.value = ''

  if (!inputUrl.value.trim()) {
    errorMessage.value = '请输入长链接'
    return
  }

  loading.value = true
  try {
    shortUrl.value = await createShortLink(inputUrl.value.trim())
  } catch (e) {
    errorMessage.value = e instanceof Error ? e.message : '请求失败'
  } finally {
    loading.value = false
  }
}

const copyShortUrl = async () => {
  if (!shortUrl.value) return
  await navigator.clipboard.writeText(shortUrl.value)
}

const openShortUrl = () => {
  if (!shortUrl.value) return
  window.open(shortUrl.value, '_blank')
}
</script>

<template>
  <main class="page">
    <section class="card">
      <h1>短链狗</h1>
      <p class="desc">输入长链接，生成可访问的短链接</p>

      <input
        v-model="inputUrl"
        type="text"
        placeholder="请输入 http/https 长链接"
        class="input"
      />

      <button class="btn" :disabled="loading" @click="createShortLinkHandler">
        {{ loading ? '生成中...' : '生成短链接' }}
      </button>

      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>

      <div v-if="shortUrl" class="result">
        <p>短链接：{{ shortUrl }}</p>
        <div class="actions">
          <button class="btn" @click="copyShortUrl">复制</button>
          <button class="btn" @click="openShortUrl">打开</button>
        </div>
      </div>
    </section>
  </main>
</template>

<style scoped>
.page {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #f5f7fb;
}

.card {
  width: 560px;
  max-width: calc(100vw - 32px);
  background: #fff;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
}

.desc {
  margin: 8px 0 16px;
  color: #666;
}

.input {
  width: 100%;
  height: 40px;
  border: 1px solid #dcdfe6;
  border-radius: 8px;
  padding: 0 12px;
  margin-bottom: 12px;
}

.btn {
  height: 36px;
  border: none;
  border-radius: 8px;
  padding: 0 14px;
  cursor: pointer;
  background: #1677ff;
  color: #fff;
  margin-right: 8px;
}

.btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.error {
  margin-top: 12px;
  color: #e53935;
}

.result {
  margin-top: 16px;
  padding: 12px;
  border-radius: 8px;
  background: #f6faff;
}

.actions {
  margin-top: 8px;
}
</style>
