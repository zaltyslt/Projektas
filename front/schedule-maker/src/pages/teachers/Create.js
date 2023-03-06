import React from "react";
import {EntityForm} from "./components/une";

export function CreateEntityPage() {
 
    function handleCreate(entity) {
    // Make an API request to create the entity
    console.log("Creating entity", entity);
  }

  return <EntityForm mode="create" onSave={handleCreate} />;
}

