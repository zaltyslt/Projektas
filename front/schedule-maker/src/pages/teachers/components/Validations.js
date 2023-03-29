export function validateText(field) {
  let fieldError = { state: false, text: "", }
  const badSymbols = "0123456789!@#$%^&*_+={}<>|;:~`/\\\"'".split("");
  const symbolsToAllow = field.symbolsToAllow.split("");
  const filterSymbols = badSymbols
    .filter((symbol) => !symbolsToAllow.includes(symbol));
  const isValid = !field.value
    .split("")
    .some((char) => filterSymbols.includes(char));
  const toLong = field.value.length > field.maxLength;
  const invalidSymbol = !isValid && "Neleistinas simbolis ! ";
  const tooLong = toLong && ("Maksimalus leistinas lauko ilgis " + field.maxLength + " simbolių !");
  const text = [invalidSymbol, tooLong].filter(Boolean).join(" ");
  return ({ error: !isValid || toLong, text: text, });
}

export function validateNumber(field) {
  const numberRegex = /^-?(0|[1-9][0-9]*)$/.test(field.value) && !/^0[0-9]/.test(field.value);
  const inInterval = field.min <= field.value && field.max >= field.value;
  const goodFormat = (inInterval && numberRegex) || field.value === "";
  const text = "Įveskte sveiką skaičių nuo " + field.min + " iki " + field.max + ".";
  return { error: !goodFormat, text: !goodFormat ? text : "", };
}

export function validateEmail(field) {
  const emailRegex = /^[a-zA-Z0-9._-]{1,20}@[a-zA-Z0-9._-]{1,10}\.[a-zA-Z0-9]{1,4}$/;
  const goodFormat = emailRegex.test(field.value) || field.value === "";
  const text = "Netinka formatas, įveskite 'xxxx{eta}xxxx.xxx'";
  return { error: !goodFormat, text: !goodFormat ? text : "", };
}

export function validatePhone(field) {
  const phoneNumberRegex = /^(?:\+370|8)6\d{7}$|^8\d{8}$/;
  const goodFormat = phoneNumberRegex.test(field.value) || field.value === "";
  const text = "Netinka formatas, įveskite '+370 000 00000' arba '8 000 00000' *be tarpų."
  return { error: !goodFormat, text: !goodFormat ? text : "", };
}

const answer = (aaa) => {
  return aaa;
}