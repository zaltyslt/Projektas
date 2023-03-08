import React from "react";
import {EntityForm} from "./components/une";
// import { useParams } from "react-router-dom";
import { Teacher } from "./Teacher";
import { postDataTo} from "./components/Fetch";

export function CreateTeacher() {
 
    async function handleCreate(teacher) {
      
      console.log(teacher);
      
        result = await postDataTo(teacher);
      
      console.log(result);
      return result;
    } 

  return <Teacher mode="create" onSave={handleCreate} />; 
}
