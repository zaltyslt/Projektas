import {
  Button,
  Container,
  Grid,
  MenuItem,
  Select,
  Stack,
  FormHelperText,
  TextField,
  Alert,
} from "@mui/material";

import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { lessons } from "../../helpers/constants";
import ".././pages.css";

export function AddShift() {
  const [name, setName] = useState("");
  const [isValidName, setIsValidName] = useState(true);
  const [isNameEmpty, setIsNameEmpty] = useState(false);
  const [isNameTooLong, setIsNameTooLong] = useState(false);

  const [shiftStartingTime, setShiftStartingTime] = useState("1");
  const [shiftEndingTime, setShiftEndingTime] = useState("1");
  const [isValidShiftTime, setIsValidShiftTime] = useState(true);

  const isActive = true;

  const [successfulPost, setSuccessfulPost] = useState();
  const [isPostUsed, setIsPostUsed] = useState(false);
  const [shiftErrors, setShiftErrors] = useState();

  const badSymbols = "!@#$%^&*_+={}<>|~`\\\"'";
  const maxLength = 200;

  const setNameAndCheck = (name) => {
    setName(name);
    name.length === 0 ? setIsNameEmpty(true) : setIsNameEmpty(false);

    const isValid = name.split("").some((char) => badSymbols.includes(char));
    !isValid ? setIsValidName(true) : setIsValidName(false);

    name.length > maxLength ? setIsNameTooLong(true) : setIsNameTooLong(false);
  };

  useEffect(() => {
    if (parseInt(shiftStartingTime) > parseInt(shiftEndingTime)) {
      setIsValidShiftTime(false);
    } else {
      setIsValidShiftTime(true);
    }
  }, [shiftStartingTime, shiftEndingTime]);

  var startIntEnum;
  var endIntEnum;

  const createShift = () => {
    if (name === "") {
      setIsNameEmpty(true);
      return;
    }
    if (isValidName && !isNameEmpty && !isNameTooLong) {
      startIntEnum = shiftStartingTime;
      endIntEnum = shiftEndingTime;
      createShiftPostRequest();
    }
  };

  const createShiftPostRequest = async () => {
    await fetch("api/v1/shift/add-shift", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name,
        startIntEnum,
        endIntEnum,
        isActive,
      }),
    })
      .then((response) => response.json())
      .then((data) => {
        handleAfterPost(data);
      });
  };

  const handleAfterPost = (data) => {
    if (data.valid) {
      setSuccessfulPost(true);
    } else {
      setShiftErrors(data);
      setSuccessfulPost(false);
    }
    setIsPostUsed(true);
  };

  return (
    <div>
      <Container>
        <h3 className="create-header"> Pridėti naują pamainą </h3>
        <form>
          <Grid container rowSpacing={2}>
            <Grid item sm={8}>
              <TextField
                fullWidth
                required
                error={!isValidName || isNameEmpty || isNameTooLong}
                helperText={
                  !isValidName
                    ? "Pavadinimas turi neleidžiamų simbolių."
                    : isNameEmpty
                    ? "Pavadinimas negali būti tuščias"
                    : isNameTooLong
                    ? "Pavadinimas negali būti ilgesnis nei 50 simbolių"
                    : null
                }
                variant="outlined"
                label="Pamainos pavadinimas"
                id="name"
                name="name"
                value={name}
                onChange={(e) => setNameAndCheck(e.target.value)}
              ></TextField>
            </Grid>
          </Grid>

          <Grid container rowSpacing={2} marginTop={1}>
            <Grid item sm={3.75} id="grid-selector">
              <h5>Pamainos pradžia:</h5>
              <Select
                fullWidth
                multiline
                error={!isValidShiftTime}
                variant="outlined"
                label="Pamainos pradžia"
                id="description"
                value={shiftStartingTime}
                onChange={(e) => setShiftStartingTime(e.target.value)}
              >
                {lessons.map((lessonTime) => (
                  <MenuItem key={lessonTime.value} value={lessonTime.value}>
                    {lessonTime.label}
                  </MenuItem>
                ))}
              </Select>
              {!isValidShiftTime && (
                <FormHelperText error>
                  Pirma pamoka negali prasidėti vėliau negu paskutinė pamoka.
                </FormHelperText>
              )}
            </Grid>

            <Grid item sm={3.75} id="grid-selector">
              <h5>Pamainos pabaiga:</h5>
              <Select
                fullWidth
                multiline
                error={!isValidShiftTime}
                variant="outlined"
                label="Pamainos pabaiga"
                id="description"
                value={shiftEndingTime}
                onChange={(e) => setShiftEndingTime(e.target.value)}
              >
                {lessons.map((lessonTime) => (
                  <MenuItem key={lessonTime.value} value={lessonTime.value}>
                    {lessonTime.label}
                  </MenuItem>
                ))}
              </Select>
              {!isValidShiftTime && (
                <FormHelperText error>
                  Pirma pamoka negali prasidėti vėliau negu paskutinė pamoka.
                </FormHelperText>
              )}
            </Grid>
          </Grid>

          <Grid item sm={2} marginTop={1}>
            <Stack direction="row" spacing={2}>
              <Button
                id="save-button-create-shift"
                variant="contained"
                onClick={createShift}
              >
                Išsaugoti
              </Button>
              <Link to="/shifts">
                <Button id="back-button-create-shift" variant="contained">
                  Grįžti
                </Button>
              </Link>
            </Stack>
          </Grid>

          <Grid container rowSpacing={2} marginTop={1}>
            <Grid item sm={8}>
              {isPostUsed ? (
                successfulPost ? (
                  <Alert severity="success"> Pamaina sėkmingai sukurta.</Alert>
                ) : (
                  <Grid>
                    <Alert severity="warning">Nepavyko sukurti pamainos.</Alert>
                    {shiftErrors.passedValidation
                      ? shiftErrors.databaseErrors.map(
                          (databaseError, index) => (
                            <Alert key={index} severity="warning">
                              {databaseError}
                            </Alert>
                          )
                        )
                      : Object.keys(shiftErrors.validationErrors).map((key) => (
                          <Alert key={key} severity="warning">
                            {" "}
                            {shiftErrors.validationErrors[key]} {key} laukelyje.
                          </Alert>
                        ))}
                  </Grid>
                )
              ) : (
                <div></div>
              )}
            </Grid>
          </Grid>
        </form>
      </Container>
    </div>
  );
}
