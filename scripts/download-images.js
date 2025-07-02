const fs = require('fs');
const path = require('path');
const https = require('https');
const { promisify } = require('util');

const writeFileAsync = promisify(fs.writeFile);
const mkdirAsync = promisify(fs.mkdir);

// 图片资源URL映射
const imageUrls = {
  // 菜品相关
  'dish/default-dish.png': 'https://cdn.materialdesignicons.com/restaurant.png',
  'dish/category/hot.png': 'https://cdn.materialdesignicons.com/fire.png',
  'dish/category/new.png': 'https://cdn.materialdesignicons.com/new-box.png',
  'dish/category/recommend.png': 'https://cdn.materialdesignicons.com/star.png',

  // 店铺相关
  'shop/logo.png': 'https://cdn.materialdesignicons.com/store.png',
  'shop/background.jpg': 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4',
  'shop/qrcode.png': 'https://cdn.materialdesignicons.com/qrcode.png',

  // 用户相关
  'user/default-avatar.png': 'https://cdn.materialdesignicons.com/account.png',
  'user/center/order.png': 'https://cdn.materialdesignicons.com/shopping.png',
  'user/center/address.png': 'https://cdn.materialdesignicons.com/map-marker.png',
  'user/center/favorite.png': 'https://cdn.materialdesignicons.com/heart.png',
  'user/center/settings.png': 'https://cdn.materialdesignicons.com/settings.png',

  // 订单相关
  'order/status/pending.png': 'https://cdn.materialdesignicons.com/clock.png',
  'order/status/paid.png': 'https://cdn.materialdesignicons.com/check-circle.png',
  'order/status/processing.png': 'https://cdn.materialdesignicons.com/progress-clock.png',
  'order/status/completed.png': 'https://cdn.materialdesignicons.com/check.png',
  'order/status/cancelled.png': 'https://cdn.materialdesignicons.com/close-circle.png',
  'order/payment/wechat.png': 'https://cdn.materialdesignicons.com/wechat.png',
  'order/payment/alipay.png': 'https://cdn.materialdesignicons.com/alipay.png',
  'order/action/pay.png': 'https://cdn.materialdesignicons.com/cash.png',
  'order/action/cancel.png': 'https://cdn.materialdesignicons.com/cancel.png',
  'order/action/delete.png': 'https://cdn.materialdesignicons.com/delete.png',

  // 通用图标
  'common/cart.png': 'https://cdn.materialdesignicons.com/cart.png',
  'common/loading.gif': 'https://cdn.materialdesignicons.com/loading.gif',
  'common/empty.png': 'https://cdn.materialdesignicons.com/package-variant.png',
  'common/error.png': 'https://cdn.materialdesignicons.com/alert-circle.png',
  'common/success.png': 'https://cdn.materialdesignicons.com/check-circle.png'
};

// 下载图片
async function downloadImage (url, filePath) {
  return new Promise((resolve, reject) => {
    const options = {
      headers: {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36'
      }
    };

    https.get(url, options, (response) => {
      if (response.statusCode !== 200) {
        reject(new Error(`Failed to download ${url}: ${response.statusCode}`));
        return;
      }

      const chunks = [];
      response.on('data', (chunk) => chunks.push(chunk));
      response.on('end', () => {
        const buffer = Buffer.concat(chunks);
        resolve(buffer);
      });
    }).on('error', reject);
  });
}

// 确保目录存在
async function ensureDirectoryExists (dirPath) {
  try {
    await mkdirAsync(dirPath, { recursive: true });
  } catch (err) {
    if (err.code !== 'EEXIST') {
      throw err;
    }
  }
}

// 主函数
async function main () {
  const baseDir = path.join(__dirname, '../miniapp/static/images');

  for (const [relativePath, url] of Object.entries(imageUrls)) {
    const filePath = path.join(baseDir, relativePath);
    const dirPath = path.dirname(filePath);

    try {
      console.log(`Downloading ${relativePath}...`);
      await ensureDirectoryExists(dirPath);
      const buffer = await downloadImage(url, filePath);
      await writeFileAsync(filePath, buffer);
      console.log(`Downloaded ${relativePath}`);
    } catch (err) {
      console.error(`Error downloading ${relativePath}:`, err);
    }
  }
}

main().catch(console.error); 