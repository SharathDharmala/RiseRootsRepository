import React from 'react';
import DryFruitsSection from './DryFruitsSection';

const DryFruitsTab = ({ items }) => {
  return (
    <div>
      <DryFruitsSection items={items} /> {/* Your existing DryFruitsSection component */}
    </div>
  );
};

export default DryFruitsTab;
