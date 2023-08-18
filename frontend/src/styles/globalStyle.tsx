import { Global, css } from '@emotion/react';

const style = css`
  html,
  body,
  div,
  span,
  applet,
  object,
  iframe,
  h1,
  h2,
  h3,
  h4,
  h5,
  h6,
  p,
  blockquote,
  pre,
  a,
  abbr,
  acronym,
  address,
  big,
  cite,
  code,
  del,
  dfn,
  em,
  img,
  ins,
  kbd,
  q,
  s,
  samp,
  small,
  strike,
  strong,
  sub,
  sup,
  tt,
  var,
  b,
  u,
  i,
  center,
  dl,
  dt,
  dd,
  ol,
  ul,
  li,
  fieldset,
  form,
  label,
  legend,
  table,
  caption,
  tbody,
  tfoot,
  thead,
  tr,
  th,
  td,
  article,
  aside,
  canvas,
  details,
  embed,
  figure,
  figcaption,
  footer,
  header,
  hgroup,
  menu,
  nav,
  output,
  ruby,
  section,
  summary,
  time,
  mark,
  audio,
  video {
    margin: 0;
    padding: 0;
    border: 0;
    font-size: 100%;
    vertical-align: baseline;
  }
  article,
  aside,
  details,
  figcaption,
  figure,
  footer,
  header,
  hgroup,
  menu,
  nav,
  section {
    display: block;
  }
  body {
    line-height: 1;
  }
  ol,
  ul {
    list-style: none;
  }
  blockquote,
  q {
    quotes: none;
  }
  blockquote:before,
  blockquote:after,
  q:before,
  q:after {
    content: '';
    content: none;
  }
  table {
    border-collapse: collapse;
    border-spacing: 0;
  }
  a {
    text-decoration: none;
    color: inherit;
  }
  button {
    padding: 0;
    background: none;
    border: 0;
    cursor: pointer;
    color: inherit;
  }
  * {
    box-sizing: border-box;
    scrollbar-width: none;
    -ms-overflow-style: none;
  }
  *::-webkit-scrollbar {
    display: none;
  }
  @font-face {
    font-family: HyundaiSansBold;
    font-style: normal;
    font-display: swap;
    src: local('HyundaiSansBold'), url('/src/assets/fonts/HyundaiSansHeadKRBold.woff2') format('woff2'),
      url('/src/assets/fonts/HyundaiSansHeadKRBold.woff') format('woff');
  }
  @font-face {
    font-family: HyundaiSansMedium;
    font-style: normal;
    font-display: swap;
    src: local('HyundaiSansMedium'), url('/src/assets/fonts/HyundaiSansHeadKRMedium.woff2') format('woff2'),
      url('/src/assets/fonts/HyundaiSansHeadKRMedium.woff') format('woff');
  }
  @font-face {
    font-family: HyundaiSansRegular;
    font-style: normal;
    font-display: swap;
    src: local('HyundaiSansRegular'), url('/src/assets/fonts/HyundaiSansHeadKRRegular.woff2') format('woff2'),
      url('/src/assets/fonts/HyundaiSansHeadKRRegular.woff') format('woff');
  }
`;

export function GlobalStyle() {
  return <Global styles={style} />;
}
