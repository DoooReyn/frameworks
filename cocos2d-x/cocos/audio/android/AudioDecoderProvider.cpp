#include "audio/android/AudioDecoderProvider.h"
#include "audio/android/AudioDecoderSLES.h"
#include "audio/android/AudioDecoderOgg.h"
#include "audio/android/AudioDecoderMp3.h"
#include "platform/CCFileUtils.h"

namespace cocos2d { namespace experimental {

AudioDecoder* AudioDecoderProvider::createAudioDecoder(SLEngineItf engineItf, const std::string &url, int bufferSizeInFrames, int sampleRate, const FdGetterCallback &fdGetterCallback)
{
    AudioDecoder* decoder = nullptr;
    std::string extension = FileUtils::getInstance()->getFileExtension(url);

    if (extension == ".ogg")
    {
        decoder = new AudioDecoderOgg();
        if (!decoder->init(url, sampleRate))
        {
            delete decoder;
            decoder = nullptr;
        }
    }
    else if (extension == ".mp3")
    {
        decoder = new AudioDecoderMp3();
        if (!decoder->init(url, sampleRate))
        {
            delete decoder;
            decoder = nullptr;
        }
    }
    else
    {
        auto slesDecoder = new AudioDecoderSLES();
        if (slesDecoder->init(engineItf, url, bufferSizeInFrames, sampleRate, fdGetterCallback))
        {
            decoder = slesDecoder;
        }
        else
        {
            delete slesDecoder;
        }
    }

    return decoder;
}

void AudioDecoderProvider::destroyAudioDecoder(AudioDecoder** decoder)
{
    if (decoder != nullptr && *decoder != nullptr)
    {
        delete (*decoder);
        (*decoder) = nullptr;
    }
}

}} // namespace cocos2d { namespace experimental {