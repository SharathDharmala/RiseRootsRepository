import React, { useEffect } from 'react';

const ImageViewer = ({ images, activeImageIndex, closeImageViewer, goToNextImage, goToPreviousImage }) => {

  // Handle key events for left and right arrows
  useEffect(() => {
    const handleKeyDown = (event) => {
      if (event.key === 'ArrowRight') {
        goToNextImage();
      } else if (event.key === 'ArrowLeft') {
        goToPreviousImage();
      }
    };

    // Add event listener
    window.addEventListener('keydown', handleKeyDown);

    // Clean up event listener on component unmount
    return () => {
      window.removeEventListener('keydown', handleKeyDown);
    };
  }, [goToNextImage, goToPreviousImage]);

  return (
    <div
      style={{
        position: 'fixed',
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        backgroundColor: 'rgba(0, 0, 0, 0.8)',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        zIndex: 1000,
      }}
    >
      <div
        style={{
          position: 'relative',
          maxWidth: '90%',
          maxHeight: '90%',
        }}
      >
        <img
          src={process.env.PUBLIC_URL + images[activeImageIndex]}
          alt="Enlarged view"
          style={{
            width: '100%',
            height: 'auto',
            objectFit: 'contain',
          }}
        />
        <button
          onClick={closeImageViewer}
          style={{
            position: 'absolute',
            top: '10px',
            right: '10px',
            backgroundColor: 'red',
            color: 'white',
            border: 'none',
            padding: '10px',
            cursor: 'pointer',
            borderRadius: '50%',
            fontSize: '16px',
          }}
        >
          X
        </button>
        <button
          onClick={goToPreviousImage}
          style={{
            position: 'absolute',
            left: '10px',
            top: '50%',
            transform: 'translateY(-50%)',
            backgroundColor: 'transparent',
            border: 'none',
            color: 'white',
            fontSize: '30px',
            cursor: 'pointer',
          }}
        >
          {'<'}
        </button>
        <button
          onClick={goToNextImage}
          style={{
            position: 'absolute',
            right: '10px',
            top: '50%',
            transform: 'translateY(-50%)',
            backgroundColor: 'transparent',
            border: 'none',
            color: 'white',
            fontSize: '30px',
            cursor: 'pointer',
          }}
        >
          {'>'}
        </button>
      </div>
    </div>
  );
};

export default ImageViewer;
