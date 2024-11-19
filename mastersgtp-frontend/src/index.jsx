import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import './index.css';


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <div style={{background:'lightgray', minWidth: '100vw', minHeight: '100vh'}}>
    <React.StrictMode>
      <App />
    </React.StrictMode>
  </div>
);

