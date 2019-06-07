
function fillOriginLanguages() {
    $("#originLanguageSelectId").empty();
    $.get("/languages")
        .done(function (data) {
            var response = data;
            for (var i = 0; i < response.length; i++) {
                var lang = response[i];
                var option = createLanguageOptionTag(lang.id, lang.name);
                $("#originLanguageSelectId").append(option);
            }
            fillTargetLanguages();
        }).fail(function () {
        throw new Error("Error retrieving origin languages")
    });
}

function fillTargetLanguages() {
    $("#targetLanguageSelectId").empty();
    var selectedOriginLangId = $('#originLanguageSelectId').find(":selected").data("lang-id");
    $.get("/languages/other/" + selectedOriginLangId)
        .done(function (data) {
            var response = data;
            for (var i = 0; i < response.length; i++) {
                var lang = response[i];
                var option = createLanguageOptionTag(lang.id, lang.name);
                $("#targetLanguageSelectId").append(option);
            }
            findCombination();
        }).fail(function () {
        throw new Error("Error retrieving target languages")
    });
}

function findCombination() {
    var selectedOriginLangId = $('#originLanguageSelectId').find(":selected").data("lang-id");
    var selectedTargetLangId = $('#targetLanguageSelectId').find(":selected").data("lang-id");
    $("#priceBoxId").empty();
    $.get("/combinations/" + selectedOriginLangId + "/" + selectedTargetLangId)
        .done(function (data) {
            var price = data.pricePerWord;
            $("#priceBoxId").text(price)
        }).fail(function () {
        throw new Error("Error retrieving target languages")
    });
}

function createLanguageOptionTag(id, message) {
    return '<option class="text-capitalize" data-lang-id="' + id + '">' + message + '</option>';
}

function uploadFileTrigger() {
    $("#uploadFileInputId").focus().trigger('click');
}

function triggerOnChangeFileInput() {
    $("#uploadFileInputId").change(function () {
        if (isExtensionAllowed(['doc', 'docx']) && isFileSizeAllowed(5000000)) {
            actionOnLoader('show');
            uploadFileAjax();
        } else {
            displayErrorUploadingFile();
        }
    });
}

function isFileSizeAllowed(maxSizeInBytes) {
    var size = $('#uploadFileInputId')[0].files[0].size;
    if (size <= maxSizeInBytes) {
        return true;
    }
    return false;
}

function isExtensionAllowed(allowedExtensions) {
    var extension = $('#uploadFileInputId').val().replace(/^.*\./, '');
    if (allowedExtensions.indexOf(extension) == -1) {
        return false;
    }
    return true;
}

function actionOnLoader(action) {
    $('#contentToWaitId').LoadingOverlay(action, {
        background: "rgba(0, 0, 0, 0.2)",
        imageColor: "white"
    });
}

function uploadFileAjax() {

    var selectedOriginLangId = $('#originLanguageSelectId').find(":selected").data("lang-id");
    var selectedTargetLangId = $('#targetLanguageSelectId').find(":selected").data("lang-id");

    var form = $('#fileUploadForm')[0];
    var data = new FormData(form);

    $.ajax({
        type: "POST",
        //enctype: 'multipart/form-data',
        url: "/files/" + selectedOriginLangId + "/" + selectedTargetLangId,
        data: data,
        contentType: false,
        processData: false,
        cache: false,
        success: function (data) {
            displayBudgetResult(data.words, data.estimatedPrice);
            actionOnLoader('hide');
        },
        error: function (e) {
            displayErrorUploadingFile();
            actionOnLoader('hide');
        }
    });
}

function displayBudgetResult(words, price) {
    $('#resultFileRowId').removeClass('d-none');
    $('#wordsCounterId').empty().text(words);
    $('#priceId').empty().text(price);
}

function displayErrorUploadingFile() {
    $('#requirementsFileId').addClass('text-danger');
    $('#uploadFileInputId').val('');
}

function onSubmitMessage() {
    $("#contactFormId").submit(function (event) {
        event.preventDefault();
        var data = {
            name: $("#contactFormId").find('[name="name"]').val(),
            email: $("#contactFormId").find('[name="email"]').val(),
            message: $("#contactFormId").find('[name="message"]').val()
        };
        sendContactMeData(data);
    });
}

function sendContactMeData(data) {
    $.post("/contactme", data)
        .done(function (data) {
            $('#SuccessMessageId').removeClass('d-none');
            $('#contactButtonId').removeClass('btn-primary').addClass('btn-success').prop('disabled', 'disabled');
        })
        .fail(function (data) {
            $('#ErrorMessageId').removeClass('d-none');

        })
        .always(function () {
            $('#ContactMeTitleId').addClass('d-none')
        });
}

function changeLocale(locale) {
    $.get("?lang=" + locale)
        .always(function () {
            window.location.reload(true);
        });

}