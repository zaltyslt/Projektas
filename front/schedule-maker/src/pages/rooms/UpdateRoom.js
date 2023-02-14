import * as React from "react";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import {
  Button,
  Container,
  FormControl,
  Grid,
  Select,
  Stack,
  TextField,
  InputLabel,
  MenuItem,
} from "@mui/material";
import { SelectChangeEvent } from "@mui/material/Select";

export function UpdateClassroom() {
  const [classroom, setClassroom] = useState({});
  const [error, setError] = useState();
  const [active, setActive] = useState("");
  const [classroomName, setClassroomName] = useState("");
  const [description, setDescription] = useState("");
  const [building, setBuilding] = useState("AKADEMIJA");

  const handleDescriptionChange = (event) => {
    setdescription(event.target.value);
  };
  const handleCNameeChange = (event) => {
    setclassroomName(event.target.value);
  };

  const handleChange = (event: SelectChangeEvent) => {
    setBuilding(event.target.value);
  };

  const params = useParams({
    classroomName: "",
    building: "",
    description: "",
  });

  useEffect(() => {
    fetch(`/api/v1/classrooms/classroom/${params.id}`)
      .then((response) => response.json())
      .then(setClassroom);
  }, []);

  const updateClassroom = () => {
    fetch(`/api/v1/classrooms/update/${params.id}`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        description,
        classroomName,
      }),
    }).then((result) => {
      if (!result.ok) {
        setError("Redaguoti nepavyko!");
      } else {
        setError("Sėkmingai atnaujinote!");
      }
    });
  };

  const disableClassroom = () => {
    fetch(`/api/v1/classrooms/disable/${params.id}`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        description,
        classroomName,
      }),
    }).then(() => navigate(-1));
  };

  const enableClassroom = () => {
    fetch(`/api/v1/classrooms/enable/${params.id}`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
    }).then(() => navigate("/rooms"));
  };

  const updateProperty = (property, event) => {
    setClassroom({
      ...classroom,
      [property]: event.target.value,
    });
  };

  return (
    <div>
      <Container>
        <h1>Redagavimas</h1>
        <h3>{classroom.classroomName}</h3>
        <h5>Paskutinį kartą redaguota: {classroom.modifiedDate}</h5>
        <form>
          <Grid container rowSpacing={2}>
            <Grid item lg={10}>
              <FormControl fullWidth>
                <InputLabel id="building-label">Pastatas</InputLabel>
                <Select
                  labelId="building-label"
                  id="building"
                  label="Pastatas"
                  value={building}
                  onChange={handleChange}
                >
                  <MenuItem value="AKADEMIJA">AKADEMIJA</MenuItem>
                  <MenuItem value="TECHIN">TECHIN</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            <Grid item lg={10}>
              <TextField
                fullWidth
                variant="outlined"
                required
                id="classroomName"
                label="Klasės pavadinimas"
                value={classroom.classroomName}
                onChange={(e) => setClassroomName(e.target.value)}
              ></TextField>
            </Grid>
            <Grid item lg={10}>
              <TextField
                fullWidth
                multiline
                labelId="building-label"
                variant="outlined"
                label="Klasės aprašas"
                id="description"
                value={classroom.description}
                onChange={handleDescriptionChange}
              ></TextField>
            </Grid>
            <Grid item lg={10}>
              {" "}
              <legend>{params.classroomName}</legend>
              {error && <div classroomName="error">{error}</div>}
            </Grid>
            <Grid item lg={10}>
              <Stack direction="row" spacing={2}>
                <Button variant="contained" onClick={updateClassroom}>
                  Išsaugoti
                </Button>
                {!classroom.active && (
                  <Button
                    variant="contained"
                    data-value="true"
                    value={params.id}
                    onClick={enableClassroom}
                  >
                    Aktyvuoti
                  </Button>
                )}
                {classroom.active && (
                  <Button
                    variant="contained"
                    data-value="true"
                    value={params.id}
                    onClick={disableClassroom}
                  >
                    Ištrinti
                  </Button>
                )}
                <Link to="/rooms">
                  <Button variant="contained">Grįžti</Button>
                </Link>
              </Stack>
            </Grid>
          </Grid>
        </form>
      </Container>
    </div>
  );
}
