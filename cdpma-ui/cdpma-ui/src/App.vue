<template>
  <router-view />
</template>

<script>
const debounce = (fn, delay) => {
  let timer = null;
  return function () {
    let context = this;
    let args = arguments;
    clearTimeout(timer);
    timer = setTimeout(function () {
      fn.apply(context, args);
    }, delay);
  };
};
// 解决ERROR ResizeObserver loop completed with undelivered notifications.
const _ResizeObserver = window.ResizeObserver;
window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
  constructor(callback) {
    callback = debounce(callback, 16);
    super(callback);
  }
};
</script>

<style>
#app{
  height: 100vh;
  margin: 0;
  padding: 0;
}

body{
  height: 100vh;
  margin: 0;
  padding: 0;
}

webpack-dev-server-overlay {
  display: none !important;
}
</style>
