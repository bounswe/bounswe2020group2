const CracoLessPlugin = require('craco-less');

module.exports = {
  plugins: [
    {
      plugin: CracoLessPlugin,
      options: {
        lessLoaderOptions: {
          lessOptions: {
            // modifyVars: { '@primary-color': '#EBD494' },
            modifyVars: { '@primary-color': '#E2BE5A' },
            javascriptEnabled: true,
          },
        },
      },
    },
  ],
};