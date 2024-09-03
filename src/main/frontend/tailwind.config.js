/** @type {import('tailwindcss').Config} */
module.exports = {
    content: ["../resources/templates/**/*.{html,js,peb}",
        './node_modules/preline/dist/*.js',
    ],
    darkMode: 'media',

    plugins: [
        require('@tailwindcss/forms'),
        require('preline/plugin'),
        // require('flowbite/plugin')

    ],

}

