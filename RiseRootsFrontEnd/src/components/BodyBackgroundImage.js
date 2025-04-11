import React, { useEffect } from 'react';

const BackgroundImage = () => {
  useEffect(() => {
    // Make sure the body has no margins and prevents scrolling.
    document.body.style.margin = '0';
    document.body.style.overflow = 'hidden';  // Prevent scrollbars if needed
    return () => {
      // Cleanup on unmount
      document.body.style.margin = '';
      document.body.style.overflow = '';
    };
  }, []);

  return (
    <div
      style={{
        position: 'absolute',
        top: 0,
        left: 0,
        width: '100%',
        height: '100vh',
        backgroundImage: `url(${process.env.PUBLIC_URL + '/background.png'})`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        zIndex: -1,  // Ensures the background stays behind everything else
      }}
    />
  );
};

export default BackgroundImage;
