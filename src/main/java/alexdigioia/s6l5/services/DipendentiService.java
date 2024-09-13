package alexdigioia.s6l5.services;

import alexdigioia.s6l5.entities.Dipendente;
import alexdigioia.s6l5.exceptions.BadRequestException;
import alexdigioia.s6l5.exceptions.NotFoundException;
import alexdigioia.s6l5.payloads.dipendenti.NewDipendenteDTO;
import alexdigioia.s6l5.repositories.DipendentiRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class DipendentiService {

    @Autowired
    private DipendentiRepository dipendentiRepository;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Dipendente save(NewDipendenteDTO body) {

        this.dipendentiRepository.findByEmail(body.email()).ifPresent(
                dipendente -> {
                    throw new BadRequestException("L'email " + body.email() + " è già in uso!");
                }
        );
        Dipendente newDipendente = new Dipendente(body.username(), body.nome(), body.cognome(), body.email(),
                "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());

        return this.dipendentiRepository.save(newDipendente);
    }

    public Page<Dipendente> findAll(int page, int size, String sortBy) {
        if (page > 100) page = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.dipendentiRepository.findAll(pageable);
    }

    public Dipendente findById(UUID idDipendente) {
        return this.dipendentiRepository.findById(idDipendente).orElseThrow(() -> new NotFoundException(idDipendente));
    }

    public Dipendente findByIdAndUpdate(UUID idDipendente, NewDipendenteDTO updatedDipendente) {
        this.dipendentiRepository.findByEmail(updatedDipendente.email()).ifPresent(
                dipendente -> {
                    throw new BadRequestException("L'email " + updatedDipendente.email() + " è già stata usata!");
                }
        );
        Dipendente found = findById(idDipendente);
        found.setUsername(updatedDipendente.username());
        found.setNome(updatedDipendente.nome());
        found.setCognome(updatedDipendente.cognome());
        found.setEmail(updatedDipendente.email());
        return this.dipendentiRepository.save(found);
    }

    public void findByIdAndDelete(UUID employeeId) {
        this.dipendentiRepository.delete(this.findById(employeeId));
    }

    public void uploadImage(UUID idDipendente, MultipartFile file) throws IOException {
        Dipendente found = findById(idDipendente);
        String avatarURL = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatarURL(avatarURL);
        this.dipendentiRepository.save(found);
    }
}
