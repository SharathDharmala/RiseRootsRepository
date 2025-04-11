import React, { useState, useEffect } from 'react';

const RealEstateSection = () => {
  const [activeTab, setActiveTab] = useState(0);
  const [realEstateCategories, setRealEstateCategories] = useState({});
  const [selectedImage, setSelectedImage] = useState(null);
  const [selectedImageIndex, setSelectedImageIndex] = useState(null);

  useEffect(() => {
    fetch('/config/properties.json')
      .then((response) => response.json())
      .then((jsonData) => {
        setRealEstateCategories(jsonData);
      })
      .catch((error) => {
        console.error('Error loading JSON file:', error);
      });
  }, []);

  const handleImageClick = (image, index) => {
    setSelectedImage(image);
    setSelectedImageIndex(index);
  };

  const closeModal = () => {
    setSelectedImage(null);
    setSelectedImageIndex(null);
  };

  const getImagesById = (id) => {
    const category = realEstateCategories[Object.keys(realEstateCategories)[activeTab]];
    const property = category?.find(property => property.id === id);
    return property ? property.images : [];
  };

  const handleNextImage = () => {
    const images = getImagesById(realEstateCategories[Object.keys(realEstateCategories)[activeTab]][activeTab].id);
    const nextIndex = (selectedImageIndex + 1) % images.length;
    setSelectedImage(images[nextIndex]);
    setSelectedImageIndex(nextIndex);
  };

  const handlePreviousImage = () => {
    const images = getImagesById(realEstateCategories[Object.keys(realEstateCategories)[activeTab]][activeTab].id);
    const prevIndex = (selectedImageIndex - 1 + images.length) % images.length;
    setSelectedImage(images[prevIndex]);
    setSelectedImageIndex(prevIndex);
  };

  return (
    <div>
      <div className="tabs">
        {Object.keys(realEstateCategories).map((category, index) => (
          <button
            key={index}
            className={activeTab === index ? 'active-tab' : ''}
            onClick={() => setActiveTab(index)}
            style={{
              padding: '12px 25px',
              margin: '0 10px',
              fontSize: '16px',
              fontWeight: '600',
              borderRadius: '10px',
              border: 'none',
              backgroundColor: activeTab === index ? '#4CAF50' : '#f1f1f1',
              color: activeTab === index ? 'white' : '#333',
              cursor: 'pointer',
              boxShadow: activeTab === index ? '0 0 8px rgba(0, 0, 0, 0.1)' : 'none',
              transition: 'background-color 0.3s ease, transform 0.3s ease, box-shadow 0.3s ease',
            }}
          >
            {category}
          </button>
        ))}
      </div>

      <div className="property-details">
        {realEstateCategories[Object.keys(realEstateCategories)[activeTab]]?.map((property) => (
          <div key={property.id} style={{ marginBottom: '20px', display: 'flex', alignItems: 'center' }}>
            
            {/* Left Side - Property Info */}
            <div style={{ flex: 1, paddingRight: '20px' }}>
			<h3
			  style={{
			    fontSize: '24px',
			    color: '#2c3e50',
			    fontWeight: '900',
			    marginBottom: '10px',
			  }}
			>
			  {property.name}
			</h3>


              <p><strong>Price:</strong> {property.price}</p>
              <p><strong>Location:</strong> {property.location}</p>
              <p><strong>Size:</strong> {property.size}</p>
            </div>

			{/* Right Side - Property Images */}
			<div style={{ flex: 1, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
			  <div
			    style={{
			      display: 'flex',
			      gap: '15px',
			      overflowX: 'auto',
			      maxWidth: '660px', // 3 * 200px + gap
			      paddingBottom: '5px',
			      scrollbarWidth: 'thin',
			    }}
			  >
			    {getImagesById(property.id).map((image, idx) => (
			      <div
			        key={idx}
			        style={{
			          width: '200px',
			          height: '200px',
			          borderRadius: '15px',
			          overflow: 'hidden',
			          boxShadow: '0 4px 12px rgba(0, 0, 0, 0.1)',
			          border: '1px solid #e0e0e0',
			          cursor: 'pointer',
			          flexShrink: 0,
			          transition: 'transform 0.3s ease',
			        }}
			        onClick={() => handleImageClick(image, idx)}
			      >
			        <img
			          src={image}
			          alt={`${property.name} Image ${idx + 1}`}
			          style={{
			            width: '100%',
			            height: '100%',
			            objectFit: 'cover',
			          }}
			        />
			      </div>
			    ))}
			  </div>
			</div>
          </div>
        ))}
      </div>

      {selectedImage && (
        <div
          style={{
            position: 'fixed',
            top: 0,
            left: 0,
            right: 0,
            bottom: 0,
            backgroundColor: 'rgba(0, 0, 0, 0.7)',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            zIndex: 1000,
          }}
          onClick={closeModal}
        >
          <button
            style={{
              position: 'absolute',
              top: '50%',
              left: '10px',
              transform: 'translateY(-50%)',
              fontSize: '24px',
              color: 'white',
              background: 'none',
              border: 'none',
              cursor: 'pointer',
            }}
            onClick={handlePreviousImage}
          >
            &#60;
          </button>

          <img
            src={selectedImage}
            alt="Selected Property"
            style={{
              maxWidth: '80%',
              maxHeight: '80%',
              objectFit: 'contain',
              cursor: 'pointer',
              borderRadius: '10px',
            }}
          />

          <button
            style={{
              position: 'absolute',
              top: '50%',
              right: '10px',
              transform: 'translateY(-50%)',
              fontSize: '24px',
              color: 'white',
              background: 'none',
              border: 'none',
              cursor: 'pointer',
            }}
            onClick={handleNextImage}
          >
            &#62;
          </button>
        </div>
      )}
    </div>
  );
};

export default RealEstateSection;
