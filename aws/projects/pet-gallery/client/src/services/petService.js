const url = `${process.env.REACT_APP_API_URL}/api/pet`;

export async function findAll() {
  const init = {
    headers: {
      'Accept': 'application/json'
    }
  };
  const response = await fetch(url, init);
  if (response.status === 200) {
    return response.json();
  }
  return Promise.reject('Unable to fetch all.');
}

export async function findById(id) {
  const init = {
    headers: {
      'Accept': 'application/json'
    }
  };
  const response = await fetch(`${url}/${id}`, init);
  if (response.status === 200) {
    return response.json();
  } else if (response === 404) {
    return Promise.reject(`Unable to find pet with id: ${id}.`);
  }
  return Promise.reject('Unable to fetch all.');
}

export async function save(pet) {
  const init = {
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    },
    body: JSON.stringify(pet)
  };

  if (pet.petId === 0) {
    init.method = 'POST';
    const response = await fetch(url, init);
    if (response.status === 400 || response.status === 409) {
      const errors = await response.json();
      return Promise.reject(errors);
    } else if (response.status === 201) {
      return response.json();
    }
    return Promise.reject(['Unable to add pet.']);
  } else {
    init.method = 'PUT';
    const response = await fetch(`${url}/${pet.petId}`, init);
    if (response.status === 400 || response.status === 409) {
      const errors = await response.json();
      return Promise.reject(errors);
    } else if (response.status === 404) {
      return Promise.reject(['Not found']);
    } else if (response.status !== 204) {
      return Promise.reject(['Unable to update pet.']);
    }
  }
}
