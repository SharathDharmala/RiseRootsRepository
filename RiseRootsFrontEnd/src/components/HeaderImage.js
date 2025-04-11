// src/components/HeaderImage.js
import React from 'react';

const HeaderImage = () => {
  return (
    <header
      style={{
        backgroundImage: `url(${process.env.PUBLIC_URL + '/logo.png'})`,
        backgroundSize: 'contain',
        backgroundPosition: 'center',
        backgroundRepeat: 'no-repeat',
        width: '100%',
        height: '30vh',
        display: 'flex',
        justifyContent: 'flex-start',
        alignItems: 'center',
        paddingLeft: '8%',
        color: 'white',
        textAlign: 'left',
      }}
    >
      <div className="header-content">
        <div className="header-title">RiseRoots</div>
        <div className="header-tagline">Grounded in Excellence, Rising with Innovation</div>
      </div>
    </header>
  );
};

export default HeaderImage;
