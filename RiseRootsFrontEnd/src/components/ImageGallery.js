// src/components/ImageGallery.js
import React, { useState, useEffect } from 'react';

const ImageGallery = () => {
  const [images, setImages] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);

  // Fetch images from properties.json
  useEffect(() => {
    const fetchImages = async () => {
      try {
        const response = await fetch('/config/properties.json');
        const data = await response.json();
        setImages(data.images || []); // Ensure 'images' is an array, fallback to empty array if not
      } catch (error) {
        console.error('Error loading JSON file:', error);
      }
    };

    fetchImages();
  }, []);

  // Go to the next image
  const nextImage = () => {
    setCurrentIndex((prevIndex) => (prevIndex + 1) % images.length);
  };

  // Go to the previous image
  const prevImage = () => {
    setCurrentIndex((prevIndex) => (prevIndex - 1 + images.length) % images.length);
  };

  // Handle keyboard navigation
  const handleKeyDown = (e) => {
    if (e.key === 'ArrowLeft') {
      prevImage();
    } else if (e.key === 'ArrowRight') {
      nextImage();
    }
  };

  // Attach and cleanup event listeners for keyboard events
  useEffect(() => {
    window.addEventListener('keydown', handleKeyDown);

    return () => {
      window.removeEventListener('keydown', handleKeyDown);
    };
  }, [images]);

  // Return a loading message or the gallery once images are available
  if (images.length === 0) {
    return <p></p>; // Show loading state until images are available
  }

  return (
    <div style={{ position: 'relative' }}>
      <img
        src={process.env.PUBLIC_URL + images[currentIndex].url}
        alt={`Image ${currentIndex}`}
        style={{
          width: '100%',
          height: 'auto',
          display: 'block',
          marginBottom: '20px',
        }}
      />
      
      {/* Navigation buttons */}
      <button
        onClick={prevImage}
        style={{
          position: 'absolute',
          top: '50%',
          left: '10px',
          transform: 'translateY(-50%)',
          backgroundColor: 'rgba(0, 0, 0, 0.5)',
          color: 'white',
          padding: '10px',
          border: 'none',
          cursor: 'pointer',
        }}
      >
        Prev
      </button>
      <button
        onClick={nextImage}
        style={{
          position: 'absolute',
          top: '50%',
          right: '10px',
          transform: 'translateY(-50%)',
          backgroundColor: 'rgba(0, 0, 0, 0.5)',
          color: 'white',
          padding: '10px',
          border: 'none',
          cursor: 'pointer',
        }}
      >
        Next
      </button>
    </div>
  );
};

export default ImageGallery;
