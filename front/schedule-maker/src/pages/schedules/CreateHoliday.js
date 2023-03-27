import { Alert, Button, Grid, Stack, TextField } from "@mui/material";
import { Container } from "@mui/system";
import { useState } from "react";
import { Link, useParams } from "react-router-dom";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { dateToUtc } from "../../helpers/helpers";
import "./Schedule.css";
import { BAD_SYMBOLS } from "../../helpers/constants";

export function CreateHoliday() {
  const [name, setName] = useState("");
  const [dateFrom, setDateFrom] = useState("");
  const [dateUntil, setDateUntil] = useState("");

  const [dateFromEmpty, setDateFromEmpty] = useState(false);
  const [dateUntilEmpty, setDateUntilEmpty] = useState(false);
  const [nameEmpty, setNameEmpty] = useState(false);
  const [validName, setValidName] = useState(false);

  const [errorMessageFrom, setErrorMessageFrom] = useState("");
  const [errorMessageUntil, setErrorMessageUntil] = useState("");
  const [createMessage, setCreateMessage] = useState("");
  const [error, setError] = useState("");

  const params = useParams();

  const createHoliday = () => {
      fetch(`api/v1/schedules/holidays/create-holiday/${params.id}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name,
        dateFrom: dateFrom.$d.toISOString().split("T")[0],
        dateUntil: dateUntil.$d.toISOString().split("T")[0],
      }),
    }).then((response) => {
      let success = response.ok;

      response.json().then((response) => {
        if (!success) {
          setCreateMessage("");
          setError(response.message);
        } else {
          setCreateMessage("Sėkmingai sukurta. ");
          setError("");
          clear();
        }
      });
    });
  };

  const clear = () => {
    setName("");
    setDateFrom("");
    setDateUntil("");
    setError("");
  }

  const validateName = (value) => {
    setName(value);
    value.length === 0 ? setNameEmpty(true) : setNameEmpty(false);

    const isValid = value.split("").some((char) => BAD_SYMBOLS.includes(char));
    isValid ? setValidName(true) : setValidName(false);
  };

  const validateDateFrom = (value) => {
    setDateFrom(dateToUtc(value));
    if (value.length === 0) {
      setDateFromEmpty(true);
    } else {
      setDateFromEmpty(false);
      setErrorMessageFrom("");
    }
  };

  const validateDateUntil = (value) => {
    setDateUntil(dateToUtc(value));
  
    if (value.length === 0) {
      setDateUntilEmpty(true);
    } else {
      setDateUntilEmpty(false);
      setErrorMessageUntil("");
    }
  };

  const validation = () => {
    let isValid = true;
    if (name === "" || name === "undefined") {
      setNameEmpty(true);
      isValid = false;
    }

    if (validName) {
      isValid = false;
    }

    if (dateFrom === "" || dateFrom === "undefined") {
      setDateFromEmpty(true);
      setErrorMessageFrom("Privaloma pasirinkti pradžios datą.");
      isValid = false;
    } else {
      setDateFromEmpty(false);
    }

    if (dateUntil === "" || dateUntil === "undefined") {
      setDateUntilEmpty(true);
      setErrorMessageUntil("Privaloma pasirinkti pabaigos datą.");
      isValid = false;
    } else {
      setDateUntilEmpty(false);
    }

    if (dateFrom !== "" && dateUntil !== "" && dateFrom.isAfter(dateUntil)) {
      setErrorMessageUntil("Diena iki negali būti vėliau už dieną nuo.");
      setDateUntilEmpty(true);
      isValid = false;
    }

    if (
      dateFrom !== "" &&
      dateUntil !== "" &&
      dateFrom.$d.toISOString().split("T")[0] ===
        dateUntil.$d.toISOString().split("T")[0]
    ) {
      setErrorMessageUntil(
        "Pradžios ir pabaigos data negali būti ta pati diena."
      );
      setDateUntilEmpty(true);
      isValid = false;
    }

    if (isValid) {
      setCreateMessage("");
      createHoliday();
    }
  };

  return (
    <div>
      <Container>
        <h3>Atostogų planavimas</h3>
        <Grid container rowSpacing={2} spacing={2}>
          <Grid item sm={10}>
            <TextField
              fullWidth
              variant="outlined"
              label="Pavadinimas"
              id="name"
              name="name"
              value={name}
              onChange={(e) => validateName(e.target.value)}
              error={nameEmpty || validName}
              helperText={
                validName
                  ? "Laukas turi negalimų simbolių."
                  : nameEmpty
                  ? "Pavadinimas yra privalomas."
                  : ""
              }
            ></TextField>
          </Grid>

          <Grid item sm={5}>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DatePicker
                className="DatePicker"
                label="Nuo"
                format="YYYY/MM/DD"
                value={dateFrom}
                onChange={(e) => validateDateFrom(e)}
                slotProps={{
                  textField: {
                    helperText: errorMessageFrom,
                    error: dateFromEmpty,
                  },
                }}
              ></DatePicker>
            </LocalizationProvider>
          </Grid>

          <Grid item sm={5}>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DatePicker
                className="DatePicker"
                label="Iki"
                format="YYYY/MM/DD"
                value={dateUntil}
                onChange={(e) => validateDateUntil(e)}
                slotProps={{
                  textField: {
                    helperText: errorMessageUntil,
                    error: dateUntilEmpty,
                  },
                }}
              ></DatePicker>
            </LocalizationProvider>
          </Grid>

          <Grid item sm={10}>
            {error && <Alert severity="warning">{error}</Alert>}
            {createMessage && <Alert severity="success">{createMessage}</Alert>}
          </Grid>

          <Grid item sm={10}>
            <Stack direction="row" spacing={2}>
              <Button
                id="save-button-create-schedule"
                variant="contained"
                onClick={validation}
              >
                Išsaugoti
              </Button>
              <Link to={"/planning/" + params.id}>
                <Button id="back-button-create-schedule" variant="contained">
                  Grįžti
                </Button>
              </Link>
            </Stack>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
}
