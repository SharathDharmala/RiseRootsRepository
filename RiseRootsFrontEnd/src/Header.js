import React from 'react';
import './Header.css';

function Header() {
  return (
    <header 
      className="header"
      style={{ backgroundImage: `url(${process.env.PUBLIC_URL + '/logo.png'})` }}
    >
      <div className="header-content">
        <div className="header-title">RiseRoots</div>
        <div className="header-tagline">Grounded in Excellence, Rising with Innovation</div>
      </div>
    </header>
  );
}

export default Header;
