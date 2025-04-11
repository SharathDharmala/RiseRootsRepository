// src/components/Header.js
import React from 'react';

const Header = () => {
  return (
    <header
      style={{
        backgroundImage: `url(${process.env.PUBLIC_URL + '/logo.png'})`,
        backgroundSize: 'contain',         // Fit the image within header
        backgroundRepeat: 'no-repeat',     // Avoid repeating
        backgroundPosition: 'center center',
        height: '30vh',
        width: '100%',
        overflow: 'hidden',                // Hide overflow to kill scrollbars
        display: 'flex',
        justifyContent: 'center',          // Center content
        alignItems: 'center',
        padding: '0 5vw',                  // Relative padding
        boxSizing: 'border-box',
        color: 'white',
        textAlign: 'center',
      }}
    >
      <div className="header-content">
        <div
          className="header-title"
          style={{ fontSize: '2.5rem', fontWeight: 'bold' }}
        >
          RiseRoots
        </div>
        <div className="header-tagline" style={{ fontSize: '1.2rem' }}>
          Grounded in Excellence, Rising with Innovation
        </div>
      </div>
    </header>
  );
};

export default Header;
