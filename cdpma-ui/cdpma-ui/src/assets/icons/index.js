import SvgIcon from '@/components/SvgIcon'

function registerSvgIcons(app) {
  app.component('svg-icon', SvgIcon)

  const req = require.context('./svg', false, /\.svg$/)
  req.keys().forEach(req)
}

export default registerSvgIcons