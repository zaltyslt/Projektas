import React from "react";
import EntityForm from "./EntityForm";

function CreateEntityPage() {
  function handleCreate(entity) {
    // Make an API request to create the entity
    console.log("Creating entity", entity);
  }

  return <EntityForm mode="create" onSave={handleCreate} />;
}

function UpdateEntityPage({ entityId }) {
  function handleUpdate(entity) {
    // Make an API request to update the entity
    console.log("Updating entity", entity);
  }

  return <EntityForm mode="update" entityId={entityId} onSave={handleUpdate} />;
}