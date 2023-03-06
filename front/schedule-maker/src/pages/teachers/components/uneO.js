import React, { useState, useEffect } from "react";

function EntityForm({ mode, entityId, onSave }) {
  const [entity, setEntity] = useState({});

  // Fetch the entity data if in update mode
  useEffect(() => {
    if (mode === "update") {
      fetchEntityData();
    }
  }, []);

  async function fetchEntityData() {
    // Make an API request to fetch the entity data by ID
    const response = await fetch(`/api/entities/${entityId}`);
    const data = await response.json();
    setEntity(data);
  }

  function handleChange(event) {
    const { name, value } = event.target;
    setEntity((prevEntity) => ({ ...prevEntity, [name]: value }));
  }

  function handleSubmit(event) {
    event.preventDefault();
    onSave(entity);
  }

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        name="name"
        value={entity.name || ""}
        onChange={handleChange}
      />
      <input
        type="text"
        name="description"
        value={entity.description || ""}
        onChange={handleChange}
      />
      <button type="submit">{mode === "update" ? "Save" : "Create"}</button>
    </form>
  );
}

export default EntityForm;
