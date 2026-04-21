<script setup lang="ts">
import { ref } from 'vue'
import { createShortLink } from '@/api/shortLinkService'

const inputUrl = ref('')
const loading = ref(false)
const errorMessage = ref('')
const shortUrl = ref('')
const showLogo = ref(true)

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
      <header class="header">
        <img
          v-if="showLogo"
          src="/doglogo.jpg"
          alt="dog logo"
          class="logo"
          @error="showLogo = false"
        />
        <div>
          <h1>短链狗</h1>
          <p class="desc">输入长链接，生成可访问的短链接</p>
        </div>
      </header>

      <input
        v-model="inputUrl"
        type="text"
        placeholder="请输入 http/https 长链接"
        class="input"
      />

      <button class="btn primary" :disabled="loading" @click="createShortLinkHandler">
        {{ loading ? '生成中...' : '生成短链接' }}
      </button>

      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>

      <div v-if="shortUrl" class="result">
        <p class="label">短链接</p>
        <p class="url">{{ shortUrl }}</p>
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
  padding: 16px;
  background: radial-gradient(circle at top, #eef4ff 0%, #f5f7fb 45%, #eef2f9 100%);
}

.card {
  width: 560px;
  max-width: 100%;
  background: #ffffff;
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 12px 32px rgba(16, 24, 40, 0.12);
  border: 1px solid #e8edf5;
}

.header {
  display: flex;
  align-items: center;
  gap: 14px;
  margin-bottom: 16px;
}

.logo {
  width: 44px;
  height: 44px;
  border-radius: 12px;
  object-fit: cover;
  border: 1px solid #e8edf5;
}

h1 {
  margin: 0;
  font-size: 24px;
  line-height: 1.2;
  color: #1f2a37;
}

.desc {
  margin: 4px 0 0;
  color: #667085;
  font-size: 14px;
}

.input {
  width: 100%;
  height: 42px;
  border: 1px solid #d0d7e2;
  border-radius: 10px;
  padding: 0 12px;
  margin-bottom: 12px;
  outline: none;
  transition: border-color 0.2s, box-shadow 0.2s;
}

.input:focus {
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.15);
}

.btn {
  height: 36px;
  border: 1px solid #d0d7e2;
  border-radius: 8px;
  padding: 0 14px;
  cursor: pointer;
  background: #fff;
  color: #1f2a37;
  margin-right: 8px;
}

.btn.primary {
  border: none;
  background: #1677ff;
  color: #fff;
}

.btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.error {
  margin-top: 10px;
  color: #e53935;
  font-size: 14px;
}

.result {
  margin-top: 16px;
  padding: 14px;
  border-radius: 10px;
  background: #f7faff;
  border: 1px solid #e2eefe;
}

.label {
  font-size: 12px;
  color: #667085;
  margin-bottom: 4px;
}

.url {
  font-weight: 600;
  color: #0f172a;
  word-break: break-all;
}

.actions {
  margin-top: 10px;
}
</style>
