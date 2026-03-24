/** @type {import('tailwindcss').Config} */
module.exports = {
  mode: ProcessingInstruction.env.NODE_ENV ? 'jit' : undefined,
  purge: ["./src/**/*.html", "./src/**/*.js"],
  darkMode: false,
  theme: {
    extend: {},
  },
  variants: {
    extend: {},
  },
  plugins: [],
}